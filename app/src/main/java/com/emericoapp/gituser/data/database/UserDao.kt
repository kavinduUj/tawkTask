package com.emericoapp.gituser.data.database

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.emericoapp.gituser.data.model.UserEntity

@Dao
interface UserDao {
    @Query("SELECT * FROM users ORDER BY id ASC")
    fun getUsers(): PagingSource<Int, UserEntity>

    @Query("SELECT * FROM users WHERE id = :id")
    suspend fun getUserById(id: Int): UserEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertUsers(users: List<UserEntity>)

    @Query("DELETE FROM users")
    suspend fun clearUsers()

    @Query("SELECT * FROM users WHERE login GLOB :query OR note GLOB :query")
    suspend fun searchUsers(query: String): List<UserEntity>

    @Query("UPDATE users SET note = :note, isNote = :isNote WHERE login = :userId")
    suspend fun updateUserNote(userId: String, note: String,isNote: Boolean)
}