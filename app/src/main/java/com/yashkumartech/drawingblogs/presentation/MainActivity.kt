package com.yashkumartech.drawingblogs.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.yashkumartech.drawingblogs.presentation.Home.HomeScreen
import com.yashkumartech.drawingblogs.presentation.Home.UserViewModel
import com.yashkumartech.drawingblogs.presentation.Login.LoginScreen
import com.yashkumartech.drawingblogs.presentation.Post.PostScreen
import com.yashkumartech.drawingblogs.presentation.Post.PostViewModel
import com.yashkumartech.drawingblogs.presentation.Register.RegisterScreen
import com.yashkumartech.drawingblogs.presentation.UploadPost.UploadScreen
import com.yashkumartech.drawingblogs.presentation.UploadPost.UploadScreenViewModel
import com.yashkumartech.drawingblogs.ui.theme.DrawingBlogsTheme
import com.yashkumartech.drawingblogs.util.Routes
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DrawingBlogsTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    val userViewModel = hiltViewModel<UserViewModel>()
                    val postViewModel = hiltViewModel<PostViewModel>()
                    val uploadScreenViewModel = hiltViewModel<UploadScreenViewModel>()
                    val lifecycleOwner = LocalLifecycleOwner.current
                    NavHost(navController = navController, startDestination = Routes.Login.route) {
                        composable(Routes.Register.route) {
                            RegisterScreen(
                                userViewModel = userViewModel,
                                navController = navController
                            )
                        }
                        composable(Routes.Login.route) {
                            LoginScreen(
                                viewModel = userViewModel,
                                navController = navController
                            )
                        }
                        composable(Routes.Home.route) {
                            HomeScreen(
                                lifecycleOwner = lifecycleOwner,
                                homeScreenViewModel = userViewModel,
                                postViewModel = postViewModel,
                                navController = navController
                            )
                        }
                        composable(Routes.Post.route) {
                            PostScreen(
                                viewModel = postViewModel,
                                navController = navController
                            )
                        }
                        composable(Routes.Upload.route) {
                            UploadScreen(
                                lifecycleOwner = lifecycleOwner,
                                navController = navController,
                                uploadScreenViewModel = uploadScreenViewModel
                            )
                        }
                    }
                }
            }
        }
    }
}
