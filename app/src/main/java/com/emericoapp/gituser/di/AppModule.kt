package com.emericoapp.gituser.di

import android.content.Context
import androidx.room.Room
import com.emericoapp.gituser.data.database.AppDatabase
import com.emericoapp.gituser.data.database.UserDao
import com.emericoapp.gituser.data.database.UserInfoDao
import com.emericoapp.gituser.data.network.ApiService
import com.emericoapp.gituser.data.repository.UserInfoRepository
import com.emericoapp.gituser.data.repository.UserRepository
import com.emericoapp.gituser.utils.Const.BASE_URL
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Dispatcher
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDispatcher(): Dispatcher {
        return Dispatcher().apply {
            maxRequests = 1
        }
    }

    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(dispatcher: Dispatcher,loggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .dispatcher(dispatcher)
            .addInterceptor(loggingInterceptor)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase =
        Room.databaseBuilder(context, AppDatabase::class.java, "user_db").build()

    @Provides
    fun provideUserDao(appDatabase: AppDatabase): UserDao = appDatabase.userDao()

    @Provides
    fun provideUserInfoDao(appDatabase: AppDatabase): UserInfoDao = appDatabase.userInfoDao()

    @Provides
    @Singleton
    fun provideUserRepository(apiService: ApiService, dao: UserDao): UserRepository =
        UserRepository(apiService, dao)

    @Provides
    @Singleton
    fun provideInfoRepo (apiService: ApiService, dao: UserInfoDao, users: UserDao): UserInfoRepository = UserInfoRepository(
        apiService,dao,users
    )
}