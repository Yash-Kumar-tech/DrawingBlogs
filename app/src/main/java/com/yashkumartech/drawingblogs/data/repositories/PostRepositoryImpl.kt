package com.yashkumartech.drawingblogs.data.repositories

import android.net.Uri
import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.*
import com.yashkumartech.drawingblogs.domain.repositories.PostRepository
import com.yashkumartech.drawingblogs.util.PostObject
import com.yashkumartech.drawingblogs.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Singleton
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

@Singleton
class PostRepositoryImpl: PostRepository {
    private val firestore = FirebaseFirestore.getInstance()
    private val db = Firebase.firestore
    override suspend fun getPosts(): Flow<Resource<List<PostObject>>> {
        return flow {
            var posts: List<PostObject> = emptyList()
            try {
                val posts1 = firestore
                    .collection("posts")
                    .get()
                    .await()
                Log.d("In postsRepository", posts1.toString())
                posts = posts1.toObjects(PostObject::class.java)
                emit(Resource.Success(posts))
            } catch(e: Exception) {
                Log.d("Error in PostRepository", e.toString())
                emit(Resource.Error(e.toString()))
            }
        }
    }

    override suspend fun uploadPost(post: PostObject): Flow<Resource<Boolean>> {
        return flow {
            emit(Resource.Loading())
            val file = Uri.parse(post.imageUrl)
            val storageRef = FirebaseStorage.getInstance().reference
            val postsRef = storageRef.child(post.hashCode().toString())
            val uploadTask = postsRef.putFile(file)

            val urlResult = suspendCoroutine<Result<Uri>> { continuation ->
                uploadTask.continueWithTask { task ->
                    if (!task.isSuccessful) {
                        task.exception?.let {
                            throw it
                        }
                    }
                    postsRef.downloadUrl
                }.addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val downloadUri = task.result
                        Log.d("Uploaded", downloadUri.toString())
                        db.collection("posts")
                            .document(post.hashCode().toString())
                            .set(post.copy(imageUrl = downloadUri.toString()))
                        continuation.resume(Result.success(downloadUri))
                    } else {
                        Log.d("Uploading error", "Upload not complete")
                        continuation.resume(Result.failure(task.exception!!))
                    }
                }
            }
            urlResult
                .onFailure {
                    emit(Resource.Error(urlResult.exceptionOrNull()?.message!!))
                }
                .onSuccess {
                    emit(Resource.Success(true))
                }
        }
    }
}