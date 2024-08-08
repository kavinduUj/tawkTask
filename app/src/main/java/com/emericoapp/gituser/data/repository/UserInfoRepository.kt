package com.emericoapp.gituser.data.repository

import com.emericoapp.gituser.data.database.UserDao
import com.emericoapp.gituser.data.database.UserInfoDao
import com.emericoapp.gituser.data.model.UserInfoEntity
import com.emericoapp.gituser.data.model.toEntityModel
import com.emericoapp.gituser.data.network.ApiService
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class UserInfoRepository @Inject constructor(
    private val apiService: ApiService,
    private val userInfoDao: UserInfoDao,
    private val userDao: UserDao
) {

    suspend fun getUserInfo(username: String): UserInfoEntity? {

        val cachedUserInfo = userInfoDao.getUserInfo(username)

        return try {
            val userInfoDto = apiService.getUserInfo(username)
            val newUserInfoEntity = userInfoDto.toEntityModel()

            val userInfoEntity = if (cachedUserInfo != null) {
                newUserInfoEntity.copy(
                    note = cachedUserInfo.note,
                )
            } else {
                newUserInfoEntity
            }

            userInfoDao.insertUserInfo(userInfoEntity)
            userInfoDao.updateUserNote(userInfoEntity.login, userInfoEntity.note!!)

            userInfoEntity
        } catch (e: IOException) {
            cachedUserInfo
        } catch (e: HttpException) {
            cachedUserInfo
        } catch (e: Exception) {
            cachedUserInfo
        }
    }

    suspend fun saveUserNote(id: String, note: String) {
        userInfoDao.updateUserNote(id, note)
        userDao.updateUserNote(id, note, note.isNotEmpty())
    }
}