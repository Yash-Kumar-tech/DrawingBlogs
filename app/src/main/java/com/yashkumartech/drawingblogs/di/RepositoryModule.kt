//package com.yashkumartech.drawingblogs.di
//
//import com.yashkumartech.drawingblogs.data.repositories.UserRepositoryImpl
//import com.yashkumartech.drawingblogs.domain.repositories.UserRepository
//import dagger.Binds
//import dagger.Module
//import dagger.Provides
//import dagger.hilt.InstallIn
//import dagger.hilt.components.SingletonComponent
//import javax.inject.Singleton
//
//@Module
//@InstallIn(SingletonComponent::class)
//abstract class RepositoryModule {
//
//    @Binds
//    @Singleton
//    abstract fun bindUserRepository(
//        userRepositoryImpl: UserRepositoryImpl
//    ): UserRepository
//
//
//}