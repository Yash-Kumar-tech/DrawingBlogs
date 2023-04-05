package com.yashkumartech.drawingblogs.presentation.Home

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.capitalize
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.yashkumartech.drawingblogs.domain.repositories.UserRepository
import com.yashkumartech.drawingblogs.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject  constructor(
    private val repository: UserRepository,
): ViewModel() {
    private val _state = MutableStateFlow(HomeScreenState())
    val state: StateFlow<HomeScreenState> = _state

    fun createUser(user: FirebaseUser?, userName: String?) {
        viewModelScope.launch {
            _state.value = HomeScreenState(
                uid = user?.uid!!,
                userName = userName!!,
                items = emptyList()
            )
            repository.createUser(user, userName)
        }
    }

    fun setUser(user: FirebaseUser?) {
        _state.value = HomeScreenState(
            uid = user?.uid!!,
            userName = state.value.userName,
            items = emptyList()
        )
        Log.d("UserViewModel uid1", user.uid+"")
        getUserName()
    }
    private fun getUserName() {
        viewModelScope.launch {
            repository
                .getUserName(FirebaseAuth.getInstance().currentUser?.uid)
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
                                _state.value = HomeScreenState(
                                    uid = state.value.uid,
                                    userName = userName.replaceFirstChar {
                                        if (it.isLowerCase()) it.titlecase(
                                            Locale.getDefault()
                                        ) else it.toString()
                                    },
                                    items = emptyList()
                                )
                            }
                        }
                    }
                }
            Log.d("UserViewModel state", state.toString())
        }
    }
}