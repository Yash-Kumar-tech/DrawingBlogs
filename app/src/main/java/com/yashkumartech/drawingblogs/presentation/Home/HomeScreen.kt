package com.yashkumartech.drawingblogs.presentation.Home

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.yashkumartech.drawingblogs.presentation.Post.PostViewModel
import com.yashkumartech.drawingblogs.util.PostObject
import com.yashkumartech.drawingblogs.util.Routes

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    homeScreenViewModel: UserViewModel = hiltViewModel(),
    postViewModel: PostViewModel = hiltViewModel(),
    navController: NavHostController,
) {
    val state by homeScreenViewModel.state.collectAsState()
    if(state.stat == "None" && state.uid != "")
        homeScreenViewModel.getPosts()
    Scaffold(
        topBar = {
            SmallTopAppBar(
                title = {
                    Text("Hello ${state.userName}")
                },
                actions = {
                    IconButton(
                        onClick = {
                            val auth = Firebase.auth
                            auth.signOut()
                            navController.popBackStack()
                        }
                    ) {
                        Icon(Icons.Default.ExitToApp, contentDescription = null)
                    }
                },
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                )
            )
        }
    ) { innerPadding ->
        if(state.isLoading) {
            Column {
                CircularProgressIndicator(modifier = Modifier.fillMaxSize(0.5f))
            }
        } else {
            LazyVerticalGrid(
                columns = GridCells.Adaptive(minSize = 200.dp),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(horizontal = 4.dp)
            ) {
                items(state.posts.size) {index ->
                    Box(
                        modifier = Modifier
                            .clickable {
                                postViewModel.setPost(
                                    state.posts[index],
                                )
                                navController.navigate(Routes.Post.route)
                            }
                    ) {
                        Post(
                            postDetails = state.posts[index]
                        )
                    }
                }
            }
        }
    }
}