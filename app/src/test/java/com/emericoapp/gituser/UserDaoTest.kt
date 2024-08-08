package com.emericoapp.gituser

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import com.emericoapp.gituser.data.database.AppDatabase
import com.emericoapp.gituser.data.database.UserDao
import com.emericoapp.gituser.data.model.UserEntity
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.*
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito


@RunWith(JUnit4::class)
class UserDaoTest {

    private lateinit var database: AppDatabase
    private lateinit var userDao: UserDao

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        val context = Mockito.mock(Context::class.java)
        database = Room.inMemoryDatabaseBuilder(
            context,
            AppDatabase::class.java
        ).allowMainThreadQueries().build()
        userDao = database.userDao()
    }

    @After
    fun tearDown() {
        database.close()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun testInsertAndGetUser() = runBlockingTest {
        val user = UserEntity(1, "login", "url", false, "")
        userDao.upsertUsers(listOf(user))

        val loaded = userDao.getUserById(1)
        Assert.assertEquals(loaded, user)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun testUpdateUserNote() = runBlockingTest {
        val user = UserEntity(1, "login", "url", false, "")
        userDao.upsertUsers(listOf(user))

        userDao.updateUserNote("login", "new note", true)
        val updatedUser = userDao.getUserById(1)
        Assert.assertEquals(updatedUser?.note, "new note")
        Assert.assertEquals(updatedUser?.isNote, true)
    }
}










