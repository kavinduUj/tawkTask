package com.emericoapp.gituser.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.emericoapp.gituser.data.database.UserDao
import com.emericoapp.gituser.data.model.UserEntity
import com.emericoapp.gituser.data.network.ApiService
import com.emericoapp.gituser.data.network.UserPagingSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val apiService: ApiService,
    private val userDao: UserDao
) {

    fun getPagedUsers(): Flow<PagingData<UserEntity>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { UserPagingSource(apiService, userDao) }
        ).flow
    }

    suspend fun searchUsers(query: String): List<UserEntity> {
        return userDao.searchUsers("*$query*")
    }
}
