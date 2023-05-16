package com.yashkumartech.drawingblogs.presentation.Home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.yashkumartech.drawingblogs.util.PostObject

@Composable
fun Post(
    postDetails: PostObject,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = Modifier
            .fillMaxWidth(0.97f)
            .padding(vertical = 4.dp, horizontal = 4.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(MaterialTheme.colorScheme.secondaryContainer)
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(postDetails.imageUrl)
                .crossfade(true)
                .build(),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .clip(RoundedCornerShape(8.dp))
                .height(300.dp)
        )
        Column(modifier = Modifier.padding(horizontal = 8.dp)) {
            Spacer(modifier = Modifier.height(16.dp))
            Text(postDetails.title)
            Spacer(modifier = Modifier.height(8.dp))
            Text(postDetails.categories, maxLines = 2, style = MaterialTheme.typography.bodyMedium)
//            Spacer(modifier = Modifier.height(8.dp))
//            Row(
//                horizontalArrangement = Arrangement.SpaceBetween,
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(horizontal = 16.dp)
//            ) {
//                IconButton(
//                    onClick = {}
//                ) {
//                    Icon(
//                        Icons.Default.Done,
//                        contentDescription = null,
//                    )
//                }
//                IconButton(
//                    onClick = {
//                        postDetails.isLiked = !postDetails.isLiked
//                        Log.d("PostItem", "Like button clicked ${postDetails.isLiked}")
//                    }
//                ) {
//                    Icon(
//                        Icons.Default.Favorite,
//                        contentDescription = null,
//                        tint = if(postDetails.isLiked) Color.Red else MaterialTheme.colorScheme.onSecondaryContainer
//                    )
//                }
//            }
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}