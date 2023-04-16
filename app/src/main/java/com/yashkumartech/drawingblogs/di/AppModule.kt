package com.yashkumartech.drawingblogs.di

import com.yashkumartech.drawingblogs.data.repositories.PostRepositoryImpl
import com.yashkumartech.drawingblogs.data.repositories.UserRepositoryImpl
import com.yashkumartech.drawingblogs.domain.repositories.PostRepository
import com.yashkumartech.drawingblogs.domain.repositories.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideUserRepository(): UserRepository {
        return UserRepositoryImpl()
    }

    @Provides
    @Singleton
    fun providePostRepository(): PostRepository {
        return PostRepositoryImpl()
    }
}