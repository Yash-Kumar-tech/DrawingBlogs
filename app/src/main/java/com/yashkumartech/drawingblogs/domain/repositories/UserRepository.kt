package com.yashkumartech.drawingblogs.domain.repositories

import com.google.firebase.auth.FirebaseUser
import com.yashkumartech.drawingblogs.domain.model.Response
import com.yashkumartech.drawingblogs.domain.model.User
import com.yashkumartech.drawingblogs.util.Resource
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun createUser(user: FirebaseUser?, userName: String, profilePhoto: String): Resource<Boolean>

    suspend fun setUser(user: FirebaseUser?): Resource<Boolean>

    suspend fun getUserDetails(uid: String): Flow<Resource<User>>

}