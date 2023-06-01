package com.yashkumartech.drawingblogs.presentation.Home

import android.net.Uri
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.yashkumartech.drawingblogs.presentation.Post.PostViewModel
import com.yashkumartech.drawingblogs.util.PostObject
import com.yashkumartech.drawingblogs.util.Routes

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    lifecycleOwner: LifecycleOwner,
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
                navigationIcon = {
                    Button(
                        onClick = {
                            navController.navigate(Routes.UserDetails.route)
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Transparent
                        )
                    ) {
                        AsyncImage(
                            model = Uri.parse(state.profilePhoto),
                            contentDescription = null,
                            modifier = Modifier
                                .clip(CircleShape)
                                .height(50.dp)
                                .aspectRatio(1f)
                                .background(Color.DarkGray),
                            contentScale = ContentScale.Crop
                        )
                    }
                },
                actions = {
                    IconButton(
                        onClick = {
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
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate(Routes.Upload.route)
                },
            ) {
                Icon(Icons.Filled.Add, contentDescription = "Add")
            }
        },
        floatingActionButtonPosition = FabPosition.Center
    ) { innerPadding ->
//        DisposableEffect(lifecycleOwner) {
//            onDispose {
//                if(navController.currentDestination != navController.findDestination(Routes.Post.route)) {
//                    Log.d("Dispose", "HomeScreen onDispose Called")
//                    homeScreenViewModel.removeUser()
//                }
//            }
//        }
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