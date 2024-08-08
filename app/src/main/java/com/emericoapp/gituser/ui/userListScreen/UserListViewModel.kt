package com.emericoapp.gituser.ui.userListScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.emericoapp.gituser.data.repository.UserRepository
import com.emericoapp.gituser.domain.model.User
import com.emericoapp.gituser.utils.toDomainModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserListViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    val users: Flow<PagingData<User>> = userRepository.getPagedUsers()
        .map { pagingData -> pagingData.map { it.toDomainModel() } }
        .cachedIn(viewModelScope)

    private val _searchResults = MutableStateFlow<List<User>>(emptyList())
    val searchResults: StateFlow<List<User>> get() = _searchResults

    fun searchUsers(query: String) {
        viewModelScope.launch {
            val result = userRepository.searchUsers(query).map { it.toDomainModel() }
            _searchResults.value = result
        }
    }
}