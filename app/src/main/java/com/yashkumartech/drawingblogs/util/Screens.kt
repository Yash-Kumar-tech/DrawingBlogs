package com.yashkumartech.drawingblogs.util

sealed class Screen(val route: String) {
    object Login : Screen("login")
    object SignUp: Screen("signup")
    object HomeScreen: Screen("home")
}