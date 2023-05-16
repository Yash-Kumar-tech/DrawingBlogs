package com.yashkumartech.drawingblogs.data.repositories

import android.util.Log
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.yashkumartech.drawingblogs.domain.model.User
import com.yashkumartech.drawingblogs.domain.repositories.UserRepository
import com.yashkumartech.drawingblogs.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Singleton

@Singleton
class UserRepositoryImpl: UserRepository {
    private val db = Firebase.firestore

    override suspend fun createUser(user: FirebaseUser?, userName: String): Resource<Boolean> {
        return try {
            val userObj = User(user!!.uid, email = user.email + "", userName = userName)
            db.collection("users")
                .document(user.uid)
                .set(userObj)
            Log.d("createUser", "Created")
            Resource.Success(true)
        } catch(e: Exception) {
            Log.d("createUser", "NotCreated")
            Resource.Error(e.toString())
        }
    }

    override suspend fun setUser(user: FirebaseUser?): Resource<Boolean> {
        return Resource.Success(true)
    }

    override suspend fun getUserName(): Flow<Resource<String>> {
        val uid = Firebase.auth.currentUser?.uid
        return flow {
            if(uid == null) {
                emit(Resource.Error("Null"))
            }
            try {
                val doc = db.collection("users")
                    .document(uid!!)
                    .get().await()
                val userName = doc.getString("userName")
                if(userName != null) {
                    emit(Resource.Success(userName))
                } else {
                    emit(Resource.Error("Error"))
                }

            } catch(e: Exception) {
                emit(Resource.Error(e.toString()))
            }
        }
    }
}