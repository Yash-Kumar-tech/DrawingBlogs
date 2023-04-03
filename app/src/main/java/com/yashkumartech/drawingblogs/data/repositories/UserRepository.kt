package com.yashkumartech.drawingblogs.data.repositories

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.yashkumartech.drawingblogs.domain.model.Response
import com.yashkumartech.drawingblogs.domain.model.User
import com.yashkumartech.drawingblogs.domain.repositories.UserRepository
import com.yashkumartech.drawingblogs.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepositoryImpl: UserRepository {
    private val db = Firebase.firestore
    private var name = ""

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

    override suspend fun getUserName(uid: String?): Flow<Resource<String>> {
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