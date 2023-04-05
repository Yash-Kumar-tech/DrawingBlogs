package com.yashkumartech.drawingblogs.util

sealed class Routes(val route: String) {
    object SignUp: Routes("signUp")
    object Login: Routes("login")
    object Home: Routes("homeScreen")
}
