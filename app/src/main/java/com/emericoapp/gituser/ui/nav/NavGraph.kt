package com.emericoapp.gituser.ui.nav

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.emericoapp.gituser.ui.theme.ThemeViewModel
import com.emericoapp.gituser.ui.userListScreen.UserListScreen
import com.emericoapp.gituser.ui.userListScreen.UserListViewModel
import com.emericoapp.gituser.ui.userProfileScreen.UserInfoViewModel
import com.emericoapp.gituser.ui.userProfileScreen.UserProfileScreen
import com.emericoapp.gituser.utils.DataStoreUtil
import com.emericoapp.gituser.utils.Navigate.USER_LIST
import com.emericoapp.gituser.utils.Navigate.USER_PROFILE

@Composable
fun NavGraph(
    startDestination: String = "userList",
    themeViewModel: ThemeViewModel,
    dataStoreUtil: DataStoreUtil
) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = startDestination) {
        composable(USER_LIST) {
            val userListViewModel: UserListViewModel = hiltViewModel()
            UserListScreen(
                userListViewModel,
                navController,
                themeViewModel,
                dataStoreUtil
            )
        }
        composable(
            "$USER_PROFILE/{userId}",
            arguments = listOf(navArgument("userId") { type = NavType.StringType })
        ) { backStackEntry ->
            val userInfoViewModel: UserInfoViewModel = hiltViewModel()
            UserProfileScreen(
                userId = backStackEntry.arguments?.getString("userId"),
                navController,
                userInfoViewModel
            )
        }
    }
}
