package com.yashkumartech.drawingblogs.data.repositories

import android.net.Uri
import android.util.Log
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.yashkumartech.drawingblogs.domain.model.User
import com.yashkumartech.drawingblogs.domain.repositories.UserRepository
import com.yashkumartech.drawingblogs.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Singleton
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

@Singleton
class UserRepositoryImpl: UserRepository {
    private val db = Firebase.firestore

    override suspend fun createUser(user: FirebaseUser?, userName: String, profilePhoto: String): Resource<Boolean> {
        return try {
            val photoUrl = uploadProfilePhoto(profilePhoto, user!!.uid).toString()
            val userObj = User(user.uid, email = user.email + "", userName = userName, profilePhoto = photoUrl)
            db.collection("users")
                .document(user.uid)
                .set(userObj)
            Log.d("createUser", userObj.toString())
            Resource.Success(true)
        } catch(e: Exception) {
            Log.d("createUser", "NotCreated")
            Resource.Error(e.toString())
        }
    }

    private suspend fun uploadProfilePhoto(profilePhoto: String, uid: String): Uri? = withContext(
        Dispatchers.IO) {
        val file = Uri.parse(profilePhoto)
        val storageRef = FirebaseStorage.getInstance().reference
        val imageRef = storageRef.child("images/${uid}")
        val uploadTask = imageRef.putFile(file)

        return@withContext try {
            uploadTask.await()
            imageRef.downloadUrl.await()
        } catch (e: Exception) {
            null
        }
    }

    override suspend fun setUser(user: FirebaseUser?): Resource<Boolean> {
        return Resource.Success(true)
    }

    override suspend fun getUserDetails(uid: String): Flow<Resource<User>> {
        return flow {
            if(uid == null) {
                emit(Resource.Error("Null"))
            }
            try {
                val doc = db.collection("users")
                    .document(uid!!)
                    .get().await()
                val userName = doc.getString("userName")
                val profilePhoto = doc.getString("profilePhoto")
                val email = doc.getString("email")
                if(userName != null && email != null && profilePhoto != null) {
                    emit(Resource.Success(User(uid, email, userName, profilePhoto)))
                } else {
                    emit(Resource.Error("Error"))
                }

            } catch(e: Exception) {
                emit(Resource.Error(e.toString()))
            }
        }
    }
}