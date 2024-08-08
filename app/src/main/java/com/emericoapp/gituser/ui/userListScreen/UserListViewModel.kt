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

/**
 * this is the user list view model class i have used
 */
@HiltViewModel
class UserListViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    // here i have collected the Flow data and map into Domain model class, so after i have use "users" this one to access the data from the UI level
    val users: Flow<PagingData<User>> = userRepository.getPagedUsers()
        .map { pagingData -> pagingData.map { it.toDomainModel() } }
        .cachedIn(viewModelScope)

    // from here i had to get searched user to display as a search result. for that i have user mutable state here.
    // _searchResults holds the "user" which is searched.
    // searchResults is the public one to read only access outside from the view model

    private val _searchResults = MutableStateFlow<List<User>>(emptyList())
    val searchResults: StateFlow<List<User>> get() = _searchResults

    fun searchUsers(query: String) {
        viewModelScope.launch {
            val result = userRepository.searchUsers(query).map { it.toDomainModel() }
            _searchResults.value = result
        }
    }
}