package com.yashkumartech.drawingblogs.presentation.Home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.yashkumartech.drawingblogs.domain.repositories.PostRepository
import com.yashkumartech.drawingblogs.domain.repositories.UserRepository
import com.yashkumartech.drawingblogs.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject  constructor(
    private val repository: UserRepository,
    private val postRepository: PostRepository
): ViewModel() {
    private val _state = MutableStateFlow(HomeScreenState())
    val state: StateFlow<HomeScreenState> = _state

    fun createUser(user: FirebaseUser?, userName: String?) {
        viewModelScope.launch {
            _state.value = HomeScreenState(
                uid = user?.uid!!,
                userName = userName!!,
                posts = emptyList()
            )
            repository.createUser(user, userName ?: "")
        }
    }

    fun setUser(user: FirebaseUser?) {
        _state.value = HomeScreenState(
            uid = user?.uid!!,
            userName = state.value.userName,
            posts = emptyList()
        )
        getUserName()
    }
    private fun getUserName() {
        viewModelScope.launch {
            repository
                .getUserName()
                .collect { result ->
                    when(result) {
                        is Resource.Error -> {
                            _state.value = state.value.copy(
                                userName = "Error"
                            )
                        }
                        is Resource.Loading -> {
                            _state.value = _state.value.copy(
                                userName = "Loading"
                            )
                        }
                        is Resource.Success -> {
                            result.data?.let { userName ->
                                _state.value = _state.value.copy(
                                    uid = state.value.uid,
                                    userName = userName.replaceFirstChar {
                                        if (it.isLowerCase()) it.titlecase(
                                            Locale.getDefault()
                                        ) else it.toString()
                                    }
                                )
                            }
                        }
                    }
                }
        }
    }

    fun removeUser() {
        _state.value.userName = ""
        _state.value.stat = "None"
        _state.value.uid = ""
        Firebase.auth.signOut()
    }

    fun getPosts() {
        viewModelScope.launch {
            postRepository.getPosts()
                .collect { result ->
                    when(result) {
                        is Resource.Error -> {
                            _state.value.isLoading = false
                            _state.value.stat = "Error"
                        }
                        is Resource.Loading -> {
                            _state.value.isLoading = true
                            _state.value.stat = "Loading"
                        }
                        is Resource.Success -> {
                            _state.value.isLoading = false
                            _state.value.stat = "Success"
                            result.data?.let { posts ->
                                _state.value.posts = posts
                            }
                        }
                    }
                    Log.d("Posts", state.value.posts.size.toString())
                }
        }
    }
}