package com.emericoapp.gituser.ui.theme

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel

class ThemeViewModel: ViewModel() {
    var isDarkThemeEnabled = mutableStateOf(false)
}