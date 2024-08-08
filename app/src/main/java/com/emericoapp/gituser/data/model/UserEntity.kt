package com.emericoapp.gituser.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * this is the user table entity
 */
@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey val id: Int,
    val login: String,
    val avatarUrl: String,
    val isNote: Boolean,
    val note: String
)
