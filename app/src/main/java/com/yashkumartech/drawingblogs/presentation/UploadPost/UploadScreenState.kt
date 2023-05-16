package com.yashkumartech.drawingblogs.presentation.UploadPost

data class UploadScreenState(
    var userName: String = "",
    var photo: String = "",
    var title: String = "",
    var categories: String = "",
    var state: String = "Initialized",
)