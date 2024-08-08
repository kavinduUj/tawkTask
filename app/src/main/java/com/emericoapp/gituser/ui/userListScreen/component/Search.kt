package com.emericoapp.gituser.ui.userListScreen.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp

@Composable
fun SearchBox(onSearch: (String) -> Unit) {
    val inputText = remember { mutableStateOf("") }
    val focusRequester = remember { FocusRequester() }
    val isFocused = remember { mutableStateOf(false) }

    BasicTextField(
        value = inputText.value,
        onValueChange = {
            inputText.value = it
            onSearch(inputText.value)
        },
        singleLine = true,
        textStyle = TextStyle(color = Color.Gray),
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .height(35.dp)
            .background(MaterialTheme.colorScheme.onPrimary, shape = RoundedCornerShape(50.dp))
            .border(1.dp, MaterialTheme.colorScheme.outline, shape = RoundedCornerShape(50.dp))
            .focusRequester(focusRequester)
            .onFocusChanged { focusState ->
                isFocused.value = focusState.isFocused
            },
        cursorBrush = SolidColor(Color.Gray),
        keyboardOptions = KeyboardOptions.Default.copy(
            imeAction = ImeAction.Search
        ),
        keyboardActions = KeyboardActions(
            onSearch = {
                onSearch(inputText.value)
                focusRequester.freeFocus()
            }
        ),
        decorationBox = { innerTextField ->
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search Icon",
                    tint = MaterialTheme.colorScheme.inverseSurface
                )
                Spacer(modifier = Modifier.width(8.dp))
                Box(
                    contentAlignment = Alignment.CenterStart,
                    modifier = Modifier
                        .weight(1f)
                ) {
                    if (inputText.value.isEmpty() && !isFocused.value) {
                        Text("search", color = MaterialTheme.colorScheme.inverseSurface)
                    }
                    innerTextField()
                }
                if (inputText.value.isNotEmpty()) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Search Icon",
                        tint = MaterialTheme.colorScheme.inverseSurface,
                        modifier = Modifier.clickable {
                            inputText.value = ""
                            onSearch(inputText.value)
                        }
                    )
                }
            }
        }
    )
}
