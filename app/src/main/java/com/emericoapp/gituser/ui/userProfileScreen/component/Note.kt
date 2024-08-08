package com.emericoapp.gituser.ui.userProfileScreen.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun Note(
    modifier: Modifier = Modifier,
    getNote: (String) -> Unit,
    savedNote: String
) {
    var note by remember {
        mutableStateOf(savedNote)
    }
    Box (
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 10.dp, end = 10.dp, top = 10.dp)
    ){
        OutlinedTextField (
            value = note,
            onValueChange = {
                note = it
                getNote(note)
            },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color.DarkGray,
                focusedLabelColor = Color.Gray
            ),
            label = {
                Text(
                    text = "Note",
                    fontSize = 12.sp,
                )
            },
            modifier = modifier
                .fillMaxWidth()
                .height(100.dp),
        )
    }
}