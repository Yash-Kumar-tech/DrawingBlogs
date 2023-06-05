package com.yashkumartech.drawingblogs.presentation.UserDetail

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.yashkumartech.drawingblogs.presentation.Home.UserViewModel

@Composable
fun UserDetailScreen(
    userViewModel: UserViewModel = hiltViewModel(),
    navController: NavController
) {
    val state by userViewModel.state.collectAsState()
    Text(state.userName)
    AsyncImage(
        model = Uri.parse(state.profilePhoto),
        contentDescription = null,
        modifier = Modifier
            .clip(CircleShape)
            .height(50.dp)
            .aspectRatio(1f)
            .background(Color.DarkGray),
        contentScale = ContentScale.Crop
    )
    TextButton(
        onClick = {
            navController.popBackStack()
        }
    ) {
        Text("Return")
    }
}