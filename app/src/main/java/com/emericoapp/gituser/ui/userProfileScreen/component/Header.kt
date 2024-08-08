package com.emericoapp.gituser.ui.userProfileScreen.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun Header (
    modifier: Modifier = Modifier,
    goBack: () -> Unit,
    name: String
) {
    Box (
        modifier = modifier
            .padding(top = 4.dp, bottom = 4.dp)
            .fillMaxWidth()
    ){
        Row {
            Icon(
                Icons.Default.KeyboardArrowLeft,
                contentDescription = "GoBack",
                tint = MaterialTheme.colorScheme.inverseSurface,
                modifier = modifier
                    .size(35.dp)
                    .clickable {
                        goBack()
                    }
            )
            Text(
                modifier = modifier
                    .fillMaxWidth()
                    .align(Alignment.CenterVertically),
                text = name,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.W500,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
                color = MaterialTheme.colorScheme.inverseSurface,
                style = MaterialTheme.typography.titleLarge
            )
        }
    }
}