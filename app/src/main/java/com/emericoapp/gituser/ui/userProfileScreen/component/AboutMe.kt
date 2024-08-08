package com.emericoapp.gituser.ui.userProfileScreen.component

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max

@Composable
fun AboutMe(
    modifier: Modifier = Modifier,
    name: String,
    company: String,
    blog: String
) {
    Box (
        modifier = modifier
            .padding(start = 10.dp, end = 10.dp, top = 10.dp)
            .fillMaxWidth()
            .border(
                width = 1.dp,
                color = Color.Gray,
                shape = RoundedCornerShape(4.dp)
            )
            .padding(10.dp)
    ){
        Column {
            Row {
                Text(
                    text = "Name   :   ",
                    color = MaterialTheme.colorScheme.inverseSurface,
                )
                Text(
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    text = name,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.inverseSurface,
                )
            }
            Spacer(modifier = modifier.height(7.dp))
            Row {
                Text(
                    text = "Company   :   ",
                    color = MaterialTheme.colorScheme.inverseSurface,
                )
                Text(
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    text = company,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.inverseSurface,
                )
            }
            Spacer(modifier = modifier.height(7.dp))
            Row {
                Text(
                    text = "Blog   :   ",
                    color = MaterialTheme.colorScheme.inverseSurface,
                )
                Text(
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    text = blog,
                    color = MaterialTheme.colorScheme.inverseSurface,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}