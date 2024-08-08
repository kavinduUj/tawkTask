package com.emericoapp.gituser.ui.userListScreen.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.emericoapp.gituser.R
import com.emericoapp.gituser.domain.model.User
import com.emericoapp.gituser.utils.InvertColorTransformation
import com.valentinilk.shimmer.shimmer

@Composable
fun UserItem(
    user: User, index: Int,
    selectedUser: (String) -> Unit
) {
    val showNote by remember {
        mutableStateOf(user.isNote)
    }
    Card (
        modifier = Modifier
            .background(MaterialTheme.colorScheme.surface)
            .fillMaxWidth()
            .padding(start = 10.dp, end = 10.dp, top = 4.dp, bottom = 4.dp),
        elevation = CardDefaults.elevatedCardElevation(
            defaultElevation = 4.dp
        ),colors = CardColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer,
            contentColor = MaterialTheme.colorScheme.inverseSurface,
            disabledContentColor = MaterialTheme.colorScheme.onPrimary,
            disabledContainerColor = MaterialTheme.colorScheme.onPrimary
        )
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.secondaryContainer)
        ) {
            val transformations = if (index % 4 == 3) {
                listOf(InvertColorTransformation())
            } else {
                emptyList()
            }
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(user.avatarUrl)
                    .crossfade(true)
                    .transformations(transformations)
                    .size(100)
                    .build(),
                contentDescription = null,
                modifier = Modifier
                    .size(50.dp)
                    .border(
                        width = 1.5.dp,
                        color = MaterialTheme.colorScheme.inverseSurface,
                        shape = CircleShape
                    )
                    .clip(CircleShape),
                contentScale = ContentScale.Crop,
            )
            Spacer(modifier = Modifier.width(8.dp))
            Column (
                modifier = Modifier
                    .weight(1f)
            ){
                Text(
                    text = user.login,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.W600
                )
                Text(
                    modifier = Modifier.clickable {
                        selectedUser(user.login)
                    },
                    text = stringResource(id = R.string.details)
                )
            }
            if(showNote) {
                Image(
                    painter = painterResource(id = R.drawable.baseline_note_24),
                    contentDescription = "",
                    modifier = Modifier
                        .size(24.dp)
                )
            }
        }
    }
}

@Preview(
    showSystemUi = true,
)
@Composable
fun UserPreview() {
    UserItem(user = User(
        id = 1,
        "username",
        "",
        false,
        ""
    ), index = 1, {})
}