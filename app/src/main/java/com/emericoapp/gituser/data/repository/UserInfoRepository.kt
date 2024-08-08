package com.emericoapp.gituser.data.repository

import com.emericoapp.gituser.data.database.UserDao
import com.emericoapp.gituser.data.database.UserInfoDao
import com.emericoapp.gituser.data.model.UserInfoEntity
import com.emericoapp.gituser.data.model.toEntityModel
import com.emericoapp.gituser.data.network.ApiService
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

/**
 * this is responsible repo class user information
 * here i have done constructor Injection using Hilt, to provide APIClass, UserDao class, UserInfoDao
 */

class UserInfoRepository @Inject constructor(
    private val apiService: ApiService,
    private val userInfoDao: UserInfoDao,
    private val userDao: UserDao
) {

    suspend fun getUserInfo(username: String): UserInfoEntity? {

        val cachedUserInfo = userInfoDao.getUserInfo(username)
        // this one help me to get if i have already cached user info there.
        // because here also goes to same, if, user has already some note, i need to keep it safe whether data is fetch or not from the API

        return try {
            val userInfoDto = apiService.getUserInfo(username)
            val newUserInfoEntity = userInfoDto.toEntityModel()

            val userInfoEntity = if (cachedUserInfo != null) {
                newUserInfoEntity.copy(
                    note = cachedUserInfo.note,
                )
                // here is the place i copy note form to new updated record.
            } else {
                newUserInfoEntity
            }

            // when fetch the data i am replacing the record and same time i am updating note too here
            userInfoDao.insertUserInfo(userInfoEntity)
            userInfoDao.updateUserNote(userInfoEntity.login, userInfoEntity.note!!)

            userInfoEntity // if network available it will return new data from here
        } catch (e: IOException) {
            cachedUserInfo      // if no network it will return cached user info from here if data is available
        } catch (e: HttpException) {
            cachedUserInfo      // and here if it's HttpException
        } catch (e: Exception) {
            cachedUserInfo      // and  here too
        }
    }

    // this is the function i have used for save the note. here i had to use userDao some function also.
    // because same time i had to update this note for these 2 tables. because, user list screen based on that i have to show note icon.
    suspend fun saveUserNote(id: String, note: String) {
        userInfoDao.updateUserNote(id, note)
        userDao.updateUserNote(id, note, note.isNotEmpty())         // to make my life easier i have added additional boolean values here.
    }
}