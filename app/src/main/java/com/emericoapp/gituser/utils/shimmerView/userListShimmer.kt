package com.emericoapp.gituser.utils.shimmerView


import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.emericoapp.gituser.R
import com.valentinilk.shimmer.shimmer

/**
 * this is the created shimmer effect for userList. for this i have used some library
 */
@Preview(showSystemUi = true)
@Composable
fun UserListShimmer(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.onSecondary)
    ) {
        LazyColumn {
            items(20) {
                Card(
                    modifier = Modifier
                        .background(MaterialTheme.colorScheme.onSecondary)
                        .fillMaxWidth()
                        .shimmer()
                        .padding(start = 10.dp, end = 10.dp, top = 4.dp, bottom = 4.dp),
                    elevation = CardDefaults.elevatedCardElevation(
                        defaultElevation = 4.dp
                    ), colors = CardColors(
                        containerColor = Color.White,
                        contentColor = Color.Gray,
                        disabledContentColor = Color.White,
                        disabledContainerColor = Color.White
                    )
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .padding(8.dp)
                            .fillMaxWidth()
                            .background(Color.White)
                    ) {

                        Box(
                            modifier = Modifier
                                .size(50.dp)
                                .border(
                                    width = 1.5.dp,
                                    color = Color.Gray,
                                    shape = CircleShape
                                )
                                .clip(RoundedCornerShape(50.dp))
                                .background(Color.DarkGray),
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Column(
                            modifier = Modifier
                                .weight(1f)
                        ) {
                            Text(
                                modifier = modifier
                                    .fillMaxWidth()
                                    .background(Color.DarkGray),
                                text = "",
                                style = MaterialTheme.typography.bodyLarge,
                                fontWeight = FontWeight.W600
                            )
                            Spacer(modifier = modifier.height(10.dp))
                            Text(
                                modifier = modifier
                                    .width(100.dp)
                                    .background(Color.DarkGray),
                                text = ""
                            )
                        }
                    }
                }
            }
        }
    }
}