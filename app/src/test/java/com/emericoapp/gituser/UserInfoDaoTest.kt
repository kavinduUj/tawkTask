package com.emericoapp.gituser

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.emericoapp.gituser.data.database.AppDatabase
import com.emericoapp.gituser.data.database.UserInfoDao
import com.emericoapp.gituser.data.model.UserInfoEntity
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.MockitoAnnotations

@RunWith(AndroidJUnit4::class)
class UserInfoDaoTest {

    private lateinit var database: AppDatabase
    private lateinit var userInfoDao: UserInfoDao

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun initDb() {
        MockitoAnnotations.initMocks(this);
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDatabase::class.java
        ).allowMainThreadQueries().build()
        userInfoDao = database.userInfoDao()
    }

    @After
    fun closeDb() {
        database.close()
    }

    @Test
    fun testInsertAndGetUserInfo() = runBlocking {
        val userInfo = UserInfoEntity(1, "login", "url", 10, 5, "name", "company", "blog", "")
        userInfoDao.insertUserInfo(userInfo)

        val loaded = userInfoDao.getUserInfo("login")
        assertEquals(loaded, userInfo)
    }

    @Test
    fun testUpdateUserInfoNote() = runBlocking {
        val userInfo = UserInfoEntity(1, "login", "url", 10, 5, "name", "company", "blog", "")
        userInfoDao.insertUserInfo(userInfo)

        userInfoDao.updateUserNote("login", "new note")
        val updatedUserInfo = userInfoDao.getUserInfo("login")
        assertEquals(updatedUserInfo?.note, "new note")
    }
}

