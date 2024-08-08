package com.emericoapp.gituser.data.network

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.emericoapp.gituser.data.database.UserDao
import com.emericoapp.gituser.data.model.UserEntity
import com.emericoapp.gituser.utils.retryIO
import com.emericoapp.gituser.utils.toEntityModel
import retrofit2.HttpException
import java.io.IOException

class UserPagingSource(
    private val apiService: ApiService,
    private val userDao: UserDao
) : PagingSource<Int, UserEntity>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, UserEntity> {
        return try {
            val page = params.key ?: 0
            val response = retryIO(times = 3) { apiService.getUsers(since = page)}
//            val users = response.map { it.toEntityModel() }
            val users = response.map { userDto ->
                val cachedUser = userDao.getUserById(userDto.id)
                if (cachedUser != null) {
                    userDto.toEntityModel().copy(
                        note = cachedUser.note,
                        isNote = cachedUser.isNote
                    )
                } else {
                    userDto.toEntityModel()
                }
            }
            userDao.upsertUsers(users)
            val nextKey = if (users.isEmpty()) {
                null
            } else {
                users.last().id
            }

            LoadResult.Page(
                data = users,
                prevKey = null,
                nextKey = nextKey
            )
        } catch (exception: IOException) {
            val dataFromDb = userDao.getUsers().load(params)
            return if (dataFromDb is LoadResult.Page) {
                LoadResult.Page(
                    data = dataFromDb.data,
                    prevKey = dataFromDb.prevKey,
                    nextKey = dataFromDb.nextKey
                )
            } else {
                Log.e("viewExceptions","IO: $exception")
                LoadResult.Error(exception)
            }
        } catch (exception: HttpException) {
            val dataFromDb = userDao.getUsers().load(params)
            return if (dataFromDb is LoadResult.Page) {
                LoadResult.Page(
                    data = dataFromDb.data,
                    prevKey = dataFromDb.prevKey,
                    nextKey = dataFromDb.nextKey
                )
            } else {
                Log.e("viewExceptions","http: $exception")
                LoadResult.Error(exception)
            }
        }
    }

    override fun getRefreshKey(state: PagingState<Int, UserEntity>): Int? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id
        }
    }
}

