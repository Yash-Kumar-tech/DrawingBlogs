package com.yashkumartech.drawingblogs.di

import com.yashkumartech.drawingblogs.data.repositories.UserRepositoryImpl
import com.yashkumartech.drawingblogs.domain.repositories.UserRepository
import com.yashkumartech.drawingblogs.presentation.Home.HomeScreenViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideuserRepository(): UserRepository {
        return UserRepositoryImpl()
    }
}