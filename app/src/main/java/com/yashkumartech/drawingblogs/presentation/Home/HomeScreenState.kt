package com.yashkumartech.drawingblogs.presentation.Home

import com.yashkumartech.drawingblogs.util.PostObject

data class HomeScreenState(
    var uid: String = "",
    var userName: String = "",
    var posts: List<PostObject> = emptyList(),
    var isLoading: Boolean = false,
    var stat: String = "None"
)