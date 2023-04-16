package com.yashkumartech.drawingblogs.domain.repositories

import com.google.firebase.auth.FirebaseUser
import com.yashkumartech.drawingblogs.util.PostObject
import com.yashkumartech.drawingblogs.util.Resource
import kotlinx.coroutines.flow.Flow

interface PostRepository {

    suspend fun getPosts(): Flow<Resource<List<PostObject>>>
    suspend fun like(user: FirebaseUser, post: Int): Resource<Boolean>
}