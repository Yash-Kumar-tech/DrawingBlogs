package com.yashkumartech.drawingblogs.presentation.Post

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseUser
import com.yashkumartech.drawingblogs.domain.repositories.PostRepository
import com.yashkumartech.drawingblogs.util.PostObject
import com.yashkumartech.drawingblogs.util.Resource
import com.yashkumartech.drawingblogs.util.Routes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostViewModel @Inject constructor(
    private val postRepository: PostRepository
): ViewModel() {

    val imageUrl = mutableStateOf("https://th.bing.com/th/id/OIP.xeC34bv5LMRsUS9YGkmP7QHaNK?pid=ImgDet&rs=1")
    val title = mutableStateOf("Title")
    val categories = mutableListOf<String>()
    val creator = mutableStateOf("Creator")
    val dateCreated = mutableStateOf("DD/MM/YYYY")

    fun setPost(post: PostObject) {
        imageUrl.value = post.imageUrl
        title.value = post.title
        categories.removeAll(categories)
        categories.addAll(post.categories.split(","))
        creator.value = post.creator
        dateCreated.value = post.dateCreated
    }
}