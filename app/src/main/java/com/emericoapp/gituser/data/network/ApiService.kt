package com.emericoapp.gituser.data.network

import com.emericoapp.gituser.data.model.UserDto
import com.emericoapp.gituser.data.model.UserInfoDto
import com.emericoapp.gituser.utils.Const.USER_ENP
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * this is the retrofit API service class which helps to get user list and information
 */
interface ApiService {
    @GET(USER_ENP)
    suspend fun getUsers(@Query("since") since: Int): List<UserDto>

    @GET("users/{username}")
    suspend fun getUserInfo(@Path("username") username: String): UserInfoDto
}