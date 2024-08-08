package com.emericoapp.gituser

import com.emericoapp.gituser.data.database.UserDao
import com.emericoapp.gituser.data.database.UserInfoDao
import com.emericoapp.gituser.data.model.UserInfoDto
import com.emericoapp.gituser.data.model.UserInfoEntity
import com.emericoapp.gituser.data.model.toEntityModel
import com.emericoapp.gituser.data.network.ApiService
import com.emericoapp.gituser.data.repository.UserInfoRepository
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner


@RunWith(MockitoJUnitRunner::class)
class UserInfoRepositoryTest {

    @Mock
    private lateinit var apiService: ApiService

    @Mock
    private lateinit var userInfoDao: UserInfoDao

    @Mock
    private lateinit var userDao: UserDao

    private lateinit var userInfoRepository: UserInfoRepository

    @Before
    fun setUp() {
        userInfoRepository = UserInfoRepository(apiService, userInfoDao, userDao)
    }

    @Test
    fun testGetUserInfoFromNetwork() = runBlocking {
        val userInfoDto = UserInfoDto(
            avatar_url = "url",
            bio = "null",
            blog = "blog",
            company = "company",
            created_at = "date",
            email = "null",
            events_url = "url",
            followers = 10,
            followers_url = "url",
            following = 5,
            following_url = "url",
            gists_url = "url",
            gravatar_id = "id",
            hireable = "null",
            html_url = "url",
            id = 1,
            location = "location",
            login = "login",
            name = "name",
            node_id = "node",
            organizations_url = "url",
            public_gists = 2,
            public_repos = 3,
            received_events_url = "url",
            repos_url = "url",
            site_admin = false,
            starred_url = "url",
            subscriptions_url = "url",
            twitter_username = "username",
            type = "User",
            updated_at = "date",
            url = "url"
        )
        val userInfoEntity = userInfoDto.toEntityModel()

        Mockito.`when`(apiService.getUserInfo("login")).thenReturn(userInfoDto)

        val result = userInfoRepository.getUserInfo("login")

        assertEquals(result, userInfoEntity)
        Mockito.verify(userInfoDao).insertUserInfo(userInfoEntity)
    }

    @Test
    fun testSaveUserNote() = runBlocking {
        val userInfoEntity = UserInfoEntity(
            id = 1, login = "login", avatarUrl = "url", followers = 10,
            following = 5, name = "name", company = "company", blog = "blog", note = "note"
        )
        userInfoRepository.saveUserNote("login", "new note")

        Mockito.verify(userInfoDao).updateUserNote("login", "new note")
        Mockito.verify(userDao).updateUserNote("login", "new note", true)
    }
}
