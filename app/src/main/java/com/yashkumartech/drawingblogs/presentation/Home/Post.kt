package com.yashkumartech.drawingblogs.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import coil.request.ImageRequest
import com.yashkumartech.drawingblogs.util.PostObject
import org.checkerframework.checker.units.qual.min

@Composable
fun Post(
    postDetails: PostObject
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
            Text(postDetails.description, maxLines = 2, style = MaterialTheme.typography.bodyMedium)
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                Icon(Icons.Default.ThumbUp, contentDescription = null)
                Icon(Icons.Default.Favorite, contentDescription = null)
            }
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}