package com.emericoapp.gituser.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.emericoapp.gituser.data.model.UserEntity
import com.emericoapp.gituser.data.model.UserInfoEntity

@Database(
    entities = [UserEntity::class, UserInfoEntity::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun userInfoDao(): UserInfoDao
}