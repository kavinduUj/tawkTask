package com.emericoapp.gituser.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "userInfo")
data class UserInfoEntity(
    @PrimaryKey val id: Int,
    val login: String,
    val avatarUrl: String,
    val followers: Int,
    val following: Int,
    val name: String?,
    val company: String?,
    val blog: String?,
    val note: String?
)
