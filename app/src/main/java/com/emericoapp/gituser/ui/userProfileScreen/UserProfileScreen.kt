package com.emericoapp.gituser.ui.userProfileScreen

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.emericoapp.gituser.R
import com.emericoapp.gituser.ui.userProfileScreen.component.AboutMe
import com.emericoapp.gituser.ui.userProfileScreen.component.Header
import com.emericoapp.gituser.ui.userProfileScreen.component.Profile
import com.emericoapp.gituser.ui.userProfileScreen.component.Note
import com.emericoapp.gituser.utils.Navigate.USER_LIST
import com.emericoapp.gituser.utils.NetworkMonitor
import com.emericoapp.gituser.utils.shimmerView.ProfileShimmer
import kotlinx.coroutines.delay

@Composable
fun UserProfileScreen(
    userId: String? = "",
    navController: NavHostController,
    userInfoViewModel: UserInfoViewModel,
) {
    val scrollState = rememberScrollState()     // this one helps me to keep remember states of scroll type
    val context = LocalContext.current
    var buttonEnabled by remember {
        mutableStateOf(false)       // based on the note edit, i need to enabled and disabled the save button based on this
    }
    var saveNote by remember {
        mutableStateOf("")      // this is note
    }

    val lifecycleOwner = LocalLifecycleOwner.current
    val lifecycle = lifecycleOwner.lifecycle
    val networkMonitor = remember { NetworkMonitor(context, lifecycle) }
    val isConnected by rememberUpdatedState(newValue = networkMonitor.isConnected)

    LaunchedEffect(isConnected) {
        if (isConnected) {
            userInfoViewModel.fetchUserInfo(userId ?: "")
        }
    }

    LaunchedEffect(userId) {
        userInfoViewModel.fetchUserInfo(userId ?: "")       // here i am observing data when this is Launched
    }

    val userInfoState = userInfoViewModel.userInfo.collectAsState()         // then i collect all the data to display on the UI
    val isLoading = userInfoState.value == null

    if (isLoading) {
        ProfileShimmer()
    } else {
        if (userInfoState.value == null) {
            // Display loading state
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.surface),
                contentAlignment = Alignment.Center
            ) {
                Column {
                    Image(
                        painter = painterResource(id = R.drawable.user_not),
                        contentDescription = "notFound",
                        Modifier.size(200.dp)
                    )
                }
            }
        }
        else {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.surface)
            ) {
                Column(
                    modifier = Modifier
                        .verticalScroll(scrollState)
                ) {
                    Header(
                        goBack = {
                            navController.navigate(USER_LIST)
                        },
                        name = userInfoState.value?.name ?: ""
                    )
                    HorizontalDivider()
                    Profile(
                        avatar = userInfoState.value?.avatarUrl ?: "",
                        following = userInfoState.value?.following ?: 0,
                        followers = userInfoState.value?.followers ?: 0,
                    )
                    AboutMe(
                        name = userInfoState.value?.name ?: "",
                        company = userInfoState.value?.company ?: "-",
                        blog = userInfoState.value?.blog ?: "-",
                    )
                    Note(
                        getNote = { note ->
                            buttonEnabled = note != userInfoState.value?.note
                            saveNote = note
                        }, savedNote = userInfoState.value?.note ?: ""
                    )

                    Button(
                        onClick = {
                            userInfoViewModel.saveUserNote(saveNote)
                            buttonEnabled = false
                            Toast.makeText(
                                context,
                                "Your note saved!",
                                Toast.LENGTH_LONG
                            ).show()
                        },
                        modifier = Modifier
                            .padding(start = 25.dp, end = 25.dp, top = 35.dp)
                            .fillMaxWidth()
                            .padding(10.dp),
                        colors = ButtonColors(
                            containerColor = MaterialTheme.colorScheme.tertiaryContainer,
                            contentColor = MaterialTheme.colorScheme.primary,
                            disabledContentColor = MaterialTheme.colorScheme.onSurface,
                            disabledContainerColor = MaterialTheme.colorScheme.secondary
                        ),
                        enabled = buttonEnabled
                    ) {
                        Text(
                            text = "Save"
                        )
                    }
                }
            }
        }
    }
}