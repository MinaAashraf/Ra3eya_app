package com.mina.dev.ra3eya_app.data.repository

import androidx.lifecycle.LiveData
import com.mina.dev.ra3eya_app.data.local.datasource.HomeLocalDataSource
import com.mina.dev.ra3eya_app.data.remote.HomeRemoteDataSource
import com.mina.dev.ra3eya_app.domain.model.Home
import com.mina.dev.ra3eya_app.domain.repository.HomeRepository
import com.mina.dev.ra3eya_app.domain.util.Result
import com.mina.dev.ra3eya_app.domain.util.onSuccess
import javax.inject.Inject

class HomeRepositoryImpl @Inject constructor(
    private val homeRemoteDataSource: HomeRemoteDataSource,
    private val homeLocalDataSource: HomeLocalDataSource
) :
    HomeRepository {
    override suspend fun insertHome(home: Home): Result<String> {
        return homeRemoteDataSource.insertHome(home).onSuccess {
            homeLocalDataSource.insertHome(home)
        }
    }

    override suspend fun refreshHomes(churchId: String): Result<List<Home>> {
       return homeRemoteDataSource.readHomes(churchId).onSuccess {
           homeLocalDataSource.insertHomes(it)
       }
    }

    override fun readHomes(): LiveData<List<Home>> = homeLocalDataSource.readHomes()


}