package com.mina.dev.ra3eya_app.di

import com.mina.dev.ra3eya_app.data.remote.ChurchRemoteDataSource
import com.mina.dev.ra3eya_app.data.remote.ChurchRemoteDataSourceImpl
import com.mina.dev.ra3eya_app.data.remote.HomeRemoteDataSource
import com.mina.dev.ra3eya_app.data.remote.HomeRemoteDataSourceImpl
import com.mina.dev.ra3eya_app.data.repository.ChurchRepositoryImpl
import com.mina.dev.ra3eya_app.data.repository.HomeRepositoryImpl
import com.mina.dev.ra3eya_app.domain.repository.ChurchRepository
import com.mina.dev.ra3eya_app.domain.repository.HomeRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {

    @Binds
    @Singleton
    abstract fun provideChurchRepo(churchRepository: ChurchRepositoryImpl): ChurchRepository


    @Binds
    @Singleton
    abstract fun provideChurchRemoteDataSource(churchRemoteDataSource: ChurchRemoteDataSourceImpl): ChurchRemoteDataSource

    @Binds
    @Singleton
    abstract fun provideHomeRepo(homeRepository: HomeRepositoryImpl): HomeRepository


    @Binds
    @Singleton
    abstract fun provideHomeRemoteDataSource(homeRemoteDataSource: HomeRemoteDataSourceImpl): HomeRemoteDataSource



}