package com.yashkumartech.drawingblogs.presentation.Home

import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.yashkumartech.drawingblogs.util.PostObject
import com.yashkumartech.drawingblogs.presentation.Post
import com.yashkumartech.drawingblogs.util.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Destination
fun HomeScreen(
    viewModel: HomeScreenViewModel = hiltViewModel(),
    navigator: DestinationsNavigator
) {
    val state by viewModel.state.collectAsState()
    Scaffold(
        topBar = {
            SmallTopAppBar(
                title = {
                    Text("Hello ${state.uid}")
                },
                actions = {
                    IconButton(
                        onClick = {
                            val auth = Firebase.auth
                            auth.signOut()
                            navigator.navigateUp()
                        }
                    ) {
                        Icon(Icons.Default.ExitToApp, contentDescription = null)
                    }
                },
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                )
            )
        }
    ) { innerPadding ->
        LazyVerticalGrid(
            columns = GridCells.Adaptive(minSize = 200.dp),
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 4.dp)
        ) {
            items(20) {
                Post(
                    postDetails = PostObject(
                        imageUrl = "https://funart.pro/uploads/posts/2021-04/1618579960_52-funart_pro-p-oboi-fon-chistoe-nebo-anime-57.jpg",
                        title = "$it Title",
                        description = "Testing the Post UI",
                        isLiked = (it % 2 == 0),
                        dateCreated = "DD/MM/YYYY"
                    )
                )
            }
        }
    }
}