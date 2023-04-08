package com.yashkumartech.drawingblogs.util

sealed class Routes(val route: String) {
    object register: Routes("register")
    object Login: Routes("login")
    object Home: Routes("homeScreen")
}
