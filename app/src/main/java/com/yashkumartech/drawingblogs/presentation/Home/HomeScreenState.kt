package com.yashkumartech.drawingblogs.presentation.Home

import com.google.firebase.firestore.auth.User
import com.yashkumartech.drawingblogs.util.PostObject

data class HomeScreenState(
    var uid: String = "",
    var userName: String = "",
    var items: List<PostObject> = emptyList()
)