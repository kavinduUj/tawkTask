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

/**
 * this is responsible repo class user operations
 * here i have done constructor Injection using Hilt, to provide APIClass, and Dao class
 */

class UserRepository @Inject constructor(
    private val apiService: ApiService,
    private val userDao: UserDao
) {

    // this is the function i am emit the data as a Flow/ this will helps me to collect the data as it becomes available
    fun getPagedUsers(): Flow<PagingData<UserEntity>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20, // default page size (it will be always dynamic when fetch data)
                enablePlaceholders = false
            ),
            pagingSourceFactory = { UserPagingSource(apiService, userDao) }
        ).flow
    }

    // this is the function i have used for searching users by loging and note
    suspend fun searchUsers(query: String): List<UserEntity> {
        return userDao.searchUsers("*$query*")
    }
}
