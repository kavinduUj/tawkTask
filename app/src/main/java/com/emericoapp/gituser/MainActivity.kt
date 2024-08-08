package com.emericoapp.gituser

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import com.emericoapp.gituser.ui.nav.NavGraph
import com.emericoapp.gituser.ui.theme.AppTheme
import com.emericoapp.gituser.ui.theme.ThemeViewModel
import com.emericoapp.gituser.utils.DataStoreUtil
import dagger.hilt.android.AndroidEntryPoint

/**
 * BONUS Task Completed (Skeletons,Exponential backoff, theme change)
 */

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private lateinit var dataStoreUtil: DataStoreUtil
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val themeViewModel: ThemeViewModel by viewModels()
        dataStoreUtil = DataStoreUtil(applicationContext)
        val systemTheme = when (resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
            Configuration.UI_MODE_NIGHT_YES -> { true }
            Configuration.UI_MODE_NIGHT_NO -> { false }
            else -> { false }
        }
        setContent {
            val theme = dataStoreUtil.getTheme(systemTheme).collectAsState(initial = systemTheme)
            AppTheme (
                darkTheme = theme.value
            ){
                NavGraph("userList", themeViewModel, dataStoreUtil)
            }
        }
    }
}
