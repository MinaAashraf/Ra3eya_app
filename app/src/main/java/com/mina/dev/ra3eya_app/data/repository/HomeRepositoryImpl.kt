package com.mina.dev.ra3eya_app.data.repository

import com.mina.dev.ra3eya_app.data.remote.HomeRemoteDataSource
import com.mina.dev.ra3eya_app.domain.model.Home
import com.mina.dev.ra3eya_app.domain.repository.HomeRepository
import com.mina.dev.ra3eya_app.domain.util.Result
import javax.inject.Inject

class HomeRepositoryImpl @Inject constructor(private val homeRemoteDataSource: HomeRemoteDataSource) :
    HomeRepository {
    override suspend fun insertHome(home: Home): Result<String> {
        return homeRemoteDataSource.insertHome(home)
    }
}