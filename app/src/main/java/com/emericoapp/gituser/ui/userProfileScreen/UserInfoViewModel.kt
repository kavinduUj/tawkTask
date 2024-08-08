package com.emericoapp.gituser.ui.userProfileScreen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.emericoapp.gituser.data.model.UserInfoEntity
import com.emericoapp.gituser.data.repository.UserInfoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserInfoViewModel @Inject constructor(
    private val userInfoRepository: UserInfoRepository
) : ViewModel() {

    private val _userInfo = MutableStateFlow<UserInfoEntity?>(null)
    val userInfo: StateFlow<UserInfoEntity?> = _userInfo

    fun fetchUserInfo(username: String) {
        viewModelScope.launch {
            val userInfo = userInfoRepository.getUserInfo(username)
            _userInfo.value = userInfo
        }
    }

    fun saveUserNote(note: String) {
        _userInfo.value?.let { userInfo ->
            viewModelScope.launch {
                userInfoRepository.saveUserNote(userInfo.login, note)
            }
        }
    }
}