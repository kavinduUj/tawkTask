package com.emericoapp.gituser.ui.userListScreen.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.emericoapp.gituser.R
import com.emericoapp.gituser.ui.theme.ThemeViewModel
import com.emericoapp.gituser.utils.DataStoreUtil
import kotlinx.coroutines.launch

@Composable
fun DarkThemeScreen(
    dataStoreUtil: DataStoreUtil,
    themeViewModel: ThemeViewModel,
) {
    var switchState by remember { themeViewModel.isDarkThemeEnabled }
    val coroutineScope = rememberCoroutineScope()
    Box(
        modifier = Modifier
            .padding(end = 25.dp)
            .fillMaxWidth(),
        contentAlignment = Alignment.CenterEnd
    ) {
        Row(
            modifier = Modifier
                .padding(start = 7.dp, top = 7.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
        ) {

            Switch(
                modifier = Modifier
                    .size(15.dp),
                checked = switchState,
                onCheckedChange = {
                    switchState = it
                    coroutineScope.launch {
                        dataStoreUtil.saveTheme(it)
                    }
                },
                colors = SwitchDefaults.colors(
                    checkedTrackColor = MaterialTheme.colorScheme.primary,
                    checkedThumbColor = MaterialTheme.colorScheme.onPrimary,
                ),
            )
            Spacer(modifier = Modifier.width(30.dp))
            Image(
                colorFilter = ColorFilter.tint(
                    if (switchState) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondary
                ),
                painter = if (switchState) painterResource(id = R.drawable.dark_32) else painterResource(
                    id = R.drawable.light_32
                ),
                contentDescription = "",
            )
        }
    }

}