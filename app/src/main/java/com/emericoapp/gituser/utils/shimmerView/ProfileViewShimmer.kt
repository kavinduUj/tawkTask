package com.emericoapp.gituser.utils.shimmerView

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.Icon
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.valentinilk.shimmer.shimmer

@Preview (showSystemUi = true)
@Composable
fun ProfileShimmer(modifier: Modifier = Modifier) {
    Box (
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.onSecondary)
            .shimmer()
    ){
        Column {
            Box (
                modifier = modifier
                    .padding(top = 4.dp, bottom = 4.dp)
                    .fillMaxWidth()
                    .padding(10.dp)
            ){
                Row {
                    Text(
                        modifier = modifier
                            .background(Color.DarkGray)
                            .fillMaxWidth()
                            .align(Alignment.CenterVertically),
                        text = "",
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.W500,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1,
                        style = MaterialTheme.typography.titleLarge
                    )
                }
            }
            Spacer(modifier = modifier.height(20.dp))
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
                }

                Column (
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = modifier
                        .weight(1f)
                ){
                    Text(
                        text = "",
                        modifier = modifier
                            .width(100.dp)
                            .background(Color.DarkGray),
                        style = MaterialTheme.typography.bodySmall,
                    )
                    Spacer(modifier = modifier.height(10.dp))
                    Text(
                        text = "",
                        modifier = modifier
                            .width(100.dp)
                            .background(Color.DarkGray),
                        style = MaterialTheme.typography.bodySmall,
                    )
                }

                Column (
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = modifier
                        .weight(1f)
                ){
                    Text(
                        text = "",
                        modifier = modifier
                            .width(100.dp)
                            .background(Color.DarkGray),
                        style = MaterialTheme.typography.bodySmall,
                    )
                    Spacer(modifier = modifier.height(10.dp))
                    Text(
                        text = "",
                        modifier = modifier
                            .width(100.dp)
                            .background(Color.DarkGray),
                        style = MaterialTheme.typography.bodySmall,
                    )
                }
            }
            Spacer(modifier = modifier.height(20.dp))
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
                            modifier = modifier
                                .width(100.dp)
                                .background(Color.DarkGray),
                            text = ""
                        )
                        Spacer(modifier = modifier.width(10.dp))
                        Text(
                            modifier = modifier
                                .width(200.dp)
                                .background(Color.DarkGray),
                            text = ""
                        )
                    }
                    Spacer(modifier = modifier.height(7.dp))
                    Row {
                        Text(
                            modifier = modifier
                                .width(100.dp)
                                .background(Color.DarkGray),
                            text = ""
                        )
                        Spacer(modifier = modifier.width(10.dp))
                        Text(
                            modifier = modifier
                                .width(200.dp)
                                .background(Color.DarkGray),
                            text = ""
                        )
                    }
                    Spacer(modifier = modifier.height(7.dp))
                    Row {
                        Text(
                            modifier = modifier
                                .width(100.dp)
                                .background(Color.DarkGray),
                            text = ""
                        )
                        Spacer(modifier = modifier.width(10.dp))
                        Text(
                            modifier = modifier
                                .width(200.dp)
                                .background(Color.DarkGray),
                            text = ""
                        )
                    }
                }
            }

            Spacer(modifier = modifier.height(20.dp))
            Box (
                modifier = modifier
                    .padding(start = 10.dp, end = 10.dp, top = 10.dp)
                    .fillMaxWidth()
                    .height(300.dp)
                    .background(Color.Gray)
                    .border(
                        width = 1.dp,
                        color = Color.Gray,
                        shape = RoundedCornerShape(4.dp)
                    )
                    .padding(10.dp)
            )
            Spacer(modifier = modifier.height(20.dp))
            Box (
                modifier = modifier
                    .padding(start = 10.dp, end = 10.dp, top = 10.dp)
                    .fillMaxWidth()
                    .height(50.dp)
                    .border(
                        width = 1.dp,
                        color = Color.Gray,
                        shape = RoundedCornerShape(50.dp)
                    )
                    .clip(RoundedCornerShape(50.dp))
                    .background(Color.DarkGray)
                    .padding(10.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    modifier = modifier
                        .width(100.dp)
                        .background(Color.Gray),
                    text = ""
                )
            }
        }
    }
}