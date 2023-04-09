package com.yashkumartech.drawingblogs.util

sealed class Routes(val route: String) {
    object Register: Routes("register")
    object Login: Routes("login")
    object Home: Routes("homeScreen")
}
