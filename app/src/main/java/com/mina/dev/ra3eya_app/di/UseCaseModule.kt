package com.mina.dev.ra3eya_app.di

import com.google.firebase.firestore.FirebaseFirestore
import com.mina.dev.ra3eya_app.domain.usecases.GetAddressFromLatLongUseCase
import com.mina.dev.ra3eya_app.domain.usecases.GetLatLongFromAddressUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class UseCaseModule {

    @Singleton
    @Provides
    fun provideGetLatLongFromAddressUseCase () : GetLatLongFromAddressUseCase = GetLatLongFromAddressUseCase()



    @Singleton
    @Provides
    fun provideGetAddressFromLatLongUseCase () : GetAddressFromLatLongUseCase = GetAddressFromLatLongUseCase()



}