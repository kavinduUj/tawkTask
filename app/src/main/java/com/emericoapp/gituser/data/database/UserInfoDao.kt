package com.emericoapp.gituser.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.emericoapp.gituser.data.model.UserInfoEntity

/**
 * this is userInfo Dao class which helps to insert userinfo, update note kind of things.
 */

@Dao
interface UserInfoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUserInfo(userInfo: UserInfoEntity)

    @Query("SELECT * FROM userInfo WHERE login = :userId")
    suspend fun getUserInfo(userId: String): UserInfoEntity?

    @Query("UPDATE userInfo SET note = :note WHERE login = :userId")
    suspend fun updateUserNote(userId: String, note: String)
}