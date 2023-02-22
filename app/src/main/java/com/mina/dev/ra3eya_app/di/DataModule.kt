package com.mina.dev.ra3eya_app.di

import com.mina.dev.ra3eya_app.data.local.datasource.*
import com.mina.dev.ra3eya_app.data.remote.*
import com.mina.dev.ra3eya_app.data.repository.ChurchRepositoryImpl
import com.mina.dev.ra3eya_app.data.repository.FamilyRepositoryImpl
import com.mina.dev.ra3eya_app.data.repository.HomeRepositoryImpl
import com.mina.dev.ra3eya_app.data.repository.MemberRepositoryImpl
import com.mina.dev.ra3eya_app.domain.repository.ChurchRepository
import com.mina.dev.ra3eya_app.domain.repository.FamilyRepository
import com.mina.dev.ra3eya_app.domain.repository.HomeRepository
import com.mina.dev.ra3eya_app.domain.repository.MemberRepository
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
    abstract fun provideHomeRepo(homeRepository: HomeRepositoryImpl): HomeRepository

    @Binds
    @Singleton
    abstract fun provideChurchRemoteDataSource(churchRemoteDataSource: ChurchRemoteDataSourceImpl): ChurchRemoteDataSource


    @Binds
    @Singleton
    abstract fun provideHomeRemoteDataSource(homeRemoteDataSource: HomeRemoteDataSourceImpl): HomeRemoteDataSource

    @Binds
    @Singleton
    abstract fun provideMemberRemoteDataSource(memberRemoteDataSource: MemberRemoteDataSourceImpl): MemberRemoteDataSource


    @Binds
    @Singleton
    abstract fun provideFamilyRemoteDataSource(familyRemoteDataSourceImpl: FamilyRemoteDataSourceImpl): FamilyRemoteDataSource


    @Binds
    @Singleton
    abstract fun provideMemberRepo(memberRepository: MemberRepositoryImpl): MemberRepository


    @Binds
    @Singleton
    abstract fun provideFamilyRepo(familyRepository: FamilyRepositoryImpl): FamilyRepository

    @Binds
    @Singleton
    abstract fun provideChurchLocalDataSource(churchLocalDataSource: ChurchLocalDataSourceImpl): ChurchLocalDataSource


    @Binds
    @Singleton
    abstract fun provideHomeLocalDataSource(homeLocalDataSource: HomeLocalDataSourceImpl): HomeLocalDataSource

    @Binds
    @Singleton
    abstract fun provideMemberLocalDataSource(memberLocalDataSource: MemberLocalDataSourceImpl): MemberLocalDataSource


    @Binds
    @Singleton
    abstract fun provideFamilyLocalDataSource(familyLocalDataSource: FamilyLocalDataSourceImpl): FamilyLocalDataSource



}