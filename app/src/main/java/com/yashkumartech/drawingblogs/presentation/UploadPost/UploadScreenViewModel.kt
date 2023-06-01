package com.yashkumartech.drawingblogs.presentation.UploadPost

import android.util.Log
import android.widget.Toast
import androidx.compose.ui.text.capitalize
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.yashkumartech.drawingblogs.domain.repositories.PostRepository
import com.yashkumartech.drawingblogs.domain.repositories.UserRepository
import com.yashkumartech.drawingblogs.presentation.Home.HomeScreenState
import com.yashkumartech.drawingblogs.util.PostObject
import com.yashkumartech.drawingblogs.util.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import com.yashkumartech.drawingblogs.util.Resource.*
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class UploadScreenViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val postRepository: PostRepository
): ViewModel() {
    private val _state = MutableStateFlow(UploadScreenState())
    val state: StateFlow<UploadScreenState> = _state

    init {
        getUserDetails(Firebase.auth.currentUser!!.uid)
    }
    private fun getUserDetails(uid: String) {
        viewModelScope.launch {
            userRepository
                .getUserDetails(uid)
                .collect {result ->
                    when(result) {
                        is Success -> {
                            val user = result.data!!
                            _state.value = _state.value.copy(
                                userName = user.userName.replaceFirstChar {
                                    if (it.isLowerCase()) it.titlecase(
                                        Locale.getDefault()
                                    ) else it.toString()
                                }
                            )
                        }
                        is Error -> {
                            _state.value = _state.value.copy(
                                userName = "Error"
                            )
                        }
                        is Loading -> {
                            _state.value = _state.value.copy(
                                userName = "Loading"
                            )
                        }
                    }
                }
        }
    }

    fun uploadToFirebase(post: PostObject) {
        viewModelScope.launch {
            postRepository
                .uploadPost(post)
                .collect { result ->
                    when(result) {
                        is Error -> {
                            _state.value = _state.value.copy(
                                state = "Error"
                            )
                        }
                        is Loading -> {
                            _state.value = _state.value.copy(
                                state = "Uploading"
                            )
                        }
                        is Success -> {
                            _state.value = _state.value.copy(
                                state = "Uploaded"
                            )
                        }
                    }
                }

        }
    }
}