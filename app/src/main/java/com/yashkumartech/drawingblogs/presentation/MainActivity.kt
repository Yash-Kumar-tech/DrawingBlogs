package com.yashkumartech.drawingblogs.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.yashkumartech.drawingblogs.presentation.Home.HomeScreen
import com.yashkumartech.drawingblogs.presentation.Home.HomeScreenViewModel
import com.yashkumartech.drawingblogs.presentation.Login.LoginScreen
import com.yashkumartech.drawingblogs.presentation.Register.RegisterScreen
import com.yashkumartech.drawingblogs.ui.theme.DrawingBlogsTheme
import com.yashkumartech.drawingblogs.util.Routes
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DrawingBlogsTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    val viewModel = hiltViewModel<HomeScreenViewModel>()
                    NavHost(navController = navController, startDestination = Routes.Login.route) {
                        composable(Routes.Register.route) {
                            RegisterScreen(userViewModel = viewModel, navController = navController)
                        }
                        composable(Routes.Login.route) {
                            LoginScreen(viewModel = viewModel, navController = navController)
                        }
                        composable(Routes.Home.route) {
                            val parentEntry = remember(it) {
                                navController.getBackStackEntry(Routes.Home.route)
                            }
                            HomeScreen(viewModel = viewModel, navController = navController)
                        }
                    }
                }
            }
        }
    }
}
