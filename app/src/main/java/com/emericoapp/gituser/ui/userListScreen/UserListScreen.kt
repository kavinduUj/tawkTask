package com.emericoapp.gituser.ui.userListScreen

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.emericoapp.gituser.R
import com.emericoapp.gituser.ui.theme.ThemeViewModel
import com.emericoapp.gituser.ui.userListScreen.component.DarkThemeScreen
import com.emericoapp.gituser.ui.userListScreen.component.SearchBox
import com.emericoapp.gituser.ui.userListScreen.component.UserItem
import com.emericoapp.gituser.utils.DataStoreUtil
import com.emericoapp.gituser.utils.Navigate.USER_PROFILE
import com.emericoapp.gituser.utils.NetworkMonitor
import com.emericoapp.gituser.utils.shimmerView.UserListShimmer
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun UserListScreen(
    viewModel: UserListViewModel = hiltViewModel(),
    navController: NavController,
    themeViewModel: ThemeViewModel,
    dataStoreUtil: DataStoreUtil
) {
    var query by remember { mutableStateOf("") }
    val users = viewModel.users.collectAsLazyPagingItems()          // from here i am collecting pagination data
    val searchResults by viewModel.searchResults.collectAsState()       // from here i am collecting searched user data

    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val lifecycle = lifecycleOwner.lifecycle

    val networkMonitor = remember { NetworkMonitor(context, lifecycle) }        // this is the NetworkMonitor instance
    val isConnected by rememberUpdatedState(newValue = networkMonitor.isConnected)      // using that instance i am update the network states realtime

    // this is for Shimmer effect to indicate some loader with animation
    var showShimmer by remember { mutableStateOf(true) }
    LaunchedEffect(Unit) {
        delay(3000)
        showShimmer = false
    }

    // this effect helps me to identify network status changes
    LaunchedEffect(isConnected) {
        if (isConnected) {
            if (users.loadState.refresh is LoadState.Error) {
                users.retry()           // is there are any issue he will re try to grab the data
            } else {
                users.refresh()         // if not he will refresh the existing list once internet is connected
            }
        }
    }

    Column(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.surface)
            .fillMaxSize()
    ) {
        if (showShimmer) {
            UserListShimmer()
        } else {
            if (users.itemCount > 0) {
                DarkThemeScreen(dataStoreUtil = dataStoreUtil, themeViewModel = themeViewModel)         // this is light and dark theme switch
                SearchBox(onSearch = { newQuery ->
                    query = newQuery
                    viewModel.searchUsers(query)
                })          // this is the search box here
                if (query.isNotEmpty()) {
                    if (searchResults.isEmpty()) {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(stringResource(id = R.string.userNotFound))
                        }
                    } else {
                        LazyColumn {
                            itemsIndexed(searchResults) { index, user ->
                                UserItem(user = user, index = index, selectedUser = { userName ->
                                    Log.i("viewGitData", "name: $userName")
                                    navController.navigate("$USER_PROFILE/$userName")
                                })
                            }
                        }
                    }
                } else {
                    Box(modifier = Modifier.fillMaxSize()) {
                        if (users.loadState.refresh is LoadState.Loading) {
                            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))      // this will indicate user list is loading under loading state
                        } else {
                            LazyColumn {
                                items(users.itemCount) { index ->
                                    val user = users[index]
                                    user?.let {
                                        UserItem(user = it, index, selectedUser = { searchedUser ->
                                            Log.i("viewGitData", "searchedUser: $searchedUser")
                                            navController.navigate("$USER_PROFILE/$searchedUser")
                                        })
                                    }
                                }

                                if (users.loadState.append is LoadState.Loading) {
                                    // this is the indicator to indicate loading more at the bottom of the list
                                    item {
                                        CircularProgressIndicator(
                                            modifier = Modifier
                                                .padding(16.dp)
                                                .align(Alignment.Center)
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            } else {
                // if u have no connection and no any local data it will display no data available
                Box(
                    modifier = Modifier
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.user_not),
                        contentDescription = ""
                    )
                }
            }
        }
    }
}


