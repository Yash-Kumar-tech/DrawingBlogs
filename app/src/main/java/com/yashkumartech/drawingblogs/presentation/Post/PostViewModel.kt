package com.yashkumartech.drawingblogs.presentation.Post

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseUser
import com.yashkumartech.drawingblogs.domain.repositories.PostRepository
import com.yashkumartech.drawingblogs.domain.repositories.UserRepository
import com.yashkumartech.drawingblogs.util.PostObject
import com.yashkumartech.drawingblogs.util.Resource
import com.yashkumartech.drawingblogs.util.Routes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostViewModel @Inject constructor(
    private val postRepository: PostRepository,
    private val userRepository: UserRepository
): ViewModel() {

    val imageUrl = mutableStateOf("https://th.bing.com/th/id/OIP.xeC34bv5LMRsUS9YGkmP7QHaNK?pid=ImgDet&rs=1")
    val title = mutableStateOf("Title")
    val categories = mutableListOf<String>()
    val creator = mutableStateOf("")
    val dateCreated = mutableStateOf("DD/MM/YYYY")
    val creatorName = mutableStateOf("")
    val profilePhoto = mutableStateOf("")

    fun getUserDetails() {
        viewModelScope.launch {
            userRepository
                .getUserDetails(creator.value)
                .collect { result ->
                    when(result) {
                        is Resource.Error -> {
                            creatorName.value = "Error fetching userName"
                        }
                        is Resource.Loading -> {
                            creatorName.value = ""
                        }
                        is Resource.Success -> {
                            creatorName.value = result.data!!.userName
                            profilePhoto.value = result.data.profilePhoto
                        }
                    }
                }
        }
    }

    fun setPost(post: PostObject) {
        imageUrl.value = post.imageUrl
        title.value = post.title
        categories.removeAll(categories)
        categories.addAll(post.categories.split(","))
        creator.value = post.creator
        dateCreated.value = post.dateCreated
        getUserDetails()
    }
}