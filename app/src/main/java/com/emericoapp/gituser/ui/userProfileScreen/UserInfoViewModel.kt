package com.emericoapp.gituser.ui.userProfileScreen


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.emericoapp.gituser.data.model.UserInfoEntity
import com.emericoapp.gituser.data.repository.UserInfoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * this is the user info view model class i have used for get information and save note
 */
@HiltViewModel
class UserInfoViewModel @Inject constructor(
    private val userInfoRepository: UserInfoRepository
) : ViewModel() {

    // from here i had to get userinfo to display as a user data. for that i have user mutable state here.
    // userInfo holds the "UserInfoEntity" which is fro related to user.
    // userInfo is the public one to read only access outside from the view model
    private val _userInfo = MutableStateFlow<UserInfoEntity?>(null)
    val userInfo: StateFlow<UserInfoEntity?> = _userInfo

    fun fetchUserInfo(username: String) {
        viewModelScope.launch {
            val userInfo = userInfoRepository.getUserInfo(username)
            _userInfo.value = userInfo
        }
    }

    // same goes to here
    fun saveUserNote(note: String) {
        _userInfo.value?.let { userInfo ->
            viewModelScope.launch {
                userInfoRepository.saveUserNote(userInfo.login, note)
            }
        }
    }
}