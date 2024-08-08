package com.emericoapp.gituser.ui.userProfileScreen.component


import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest

@Composable
fun Profile(
    modifier: Modifier = Modifier,
    avatar: String,
    following: Int = 0,
    followers: Int = 0
) {
    Row (
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .padding(10.dp)
    ){
        Column (
            modifier = modifier
                .weight(1f)
        ){
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(avatar)
                    .crossfade(true)
                    .size(100)
                    .build(),
                contentDescription = null,
                modifier = Modifier
                    .size(80.dp)
                    .border(
                        width = 1.5.dp,
                        color = Color.Gray,
                        shape = CircleShape
                    )
                    .clip(CircleShape),
                contentScale = ContentScale.Crop,
            )
        }

        Column (
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier
                .weight(1f)
        ){
            Text(
                color = MaterialTheme.colorScheme.inverseSurface,
                text = followers.toString(),
                style = MaterialTheme.typography.bodySmall,
            )
            Text(
                color = MaterialTheme.colorScheme.inverseSurface,
                text = "Followers",
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.W600
            )
        }

        Column (
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier
                .weight(1f)
        ){
            Text(
                color = MaterialTheme.colorScheme.inverseSurface,
                text = following.toString(),
                style = MaterialTheme.typography.bodySmall,
            )
            Text(
                color = MaterialTheme.colorScheme.inverseSurface,
                text = "Following",
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.W600
            )
        }
    }
}