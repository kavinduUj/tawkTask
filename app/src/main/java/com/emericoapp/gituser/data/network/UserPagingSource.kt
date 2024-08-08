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

/**
 * this is the pagination class i used for paging user list. this paging source coming from paging library.
 */
class UserPagingSource(
    private val apiService: ApiService,
    private val userDao: UserDao
) : PagingSource<Int, UserEntity>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, UserEntity> {
        return try {
            // 1st i am starting page key as a 0
            val page = params.key ?: 0
            val response = retryIO(times = 3) { apiService.getUsers(since = page)} // here i am call to the api to fetch data from the API
            val users = response.map { userDto ->

                // here what i have done is, when fetching the data and if user has already some note i am keeping existing note and i just update other API data only.
                // without destroying local data which is user entering
                // and also i am copy that data from the entity class to keep it safe
                val cachedUser = userDao.getUserById(userDto.id)
                if (cachedUser != null) {
                    userDto.toEntityModel().copy(
                        note = cachedUser.note,
                        isNote = cachedUser.isNote
                    )
                } else {
                    userDto.toEntityModel() // after i receive the data list i am mapping those data list to "UserDto.toEntityModel() "
                }
            }
            userDao.upsertUsers(users) // here is the place i am storing data to database

            // then after i need to have a last user id to get 2nd user list, here is the place i am getting last user id
            val nextKey = if (users.isEmpty()) {
                null
            } else {
                users.last().id
            }

            // this LoadResult.Page function for paging api data
            LoadResult.Page(
                data = users,
                prevKey = null,
                nextKey = nextKey
            )
        } catch (exception: IOException) {
            // if i got some IOException, i am checking data is available or not in the db, if data is available i get those and pass to ui

            // this LoadResult.Page function for paging database data
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
            // here also same, but this is for HttpException

            // this LoadResult.Page function for paging database data
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

