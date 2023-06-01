package com.yashkumartech.drawingblogs.presentation.Post

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.request.ImageRequest


@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun PostScreen(
    viewModel: PostViewModel = hiltViewModel(),
    navController: NavHostController
) {
    val scrollState = rememberScrollState()
    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .verticalScroll(state = scrollState, enabled = true)
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(viewModel.imageUrl.value)
                    .crossfade(true)
                    .build(),
                contentDescription = null,
                contentScale = ContentScale.FillWidth,
                modifier = Modifier
                    .fillMaxWidth()
            )
            Column(
                modifier = Modifier.padding(horizontal = 16.dp)
            ) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(viewModel.title.value)
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.height(60.dp)
                ) {
                    AsyncImage(
                        model = viewModel.profilePhoto.value,
                        contentDescription = null,
                        modifier = Modifier
                            .width(60.dp)
                            .clip(CircleShape),
                        contentScale = ContentScale.FillBounds
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Column(
                        modifier = Modifier
                            .height(60.dp)
                            .padding(vertical = 5.dp),
                        horizontalAlignment = Alignment.Start,
                        verticalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Text(text = viewModel.creatorName.value)
                        Text(text = viewModel.dateCreated.value)
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
                FlowRow{
                    viewModel.categories.forEach {
                        SuggestionChip(
                            onClick = {},
                            label = { Text(it, color = MaterialTheme.colorScheme.primary) },
                            modifier = Modifier.padding(horizontal = 4.dp)
                        )
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
                TextButton(
                    onClick = {
                        navController.popBackStack()
                    }
                ) {
                    Text("Return")
                }
            }
        }
    }
}