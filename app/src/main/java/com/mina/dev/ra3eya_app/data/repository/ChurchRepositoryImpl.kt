package com.mina.dev.ra3eya_app.data.repository

import com.mina.dev.ra3eya_app.data.remote.ChurchRemoteDataSource
import com.mina.dev.ra3eya_app.domain.model.Church
import com.mina.dev.ra3eya_app.domain.model.ChurchCredentials
import com.mina.dev.ra3eya_app.domain.repository.ChurchRepository
import com.mina.dev.ra3eya_app.domain.util.Result
import javax.inject.Inject

class ChurchRepositoryImpl @Inject constructor(private val churchRemoteDataSource: ChurchRemoteDataSource) :
    ChurchRepository {

    override suspend fun addChurch(church: Church): Result<String> =
        churchRemoteDataSource.addChurch(church)

    override suspend fun signIn(church: ChurchCredentials): Result<Church> =
        churchRemoteDataSource.signIn(church)

    override suspend fun readChurch(churchId: String): Result<Church> {
       return churchRemoteDataSource.readChurch(churchId)
    }

    override suspend fun readAllChurches(): Result<List<Church>> {
      return churchRemoteDataSource.readAllChurches()
    }


}