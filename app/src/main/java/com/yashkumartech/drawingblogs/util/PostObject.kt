package com.yashkumartech.drawingblogs.util

data class PostObject(
    val imageUrl: String,
    val title: String,
    val description: String,
    val isLiked: Boolean = false,
    val dateCreated: String,
)
