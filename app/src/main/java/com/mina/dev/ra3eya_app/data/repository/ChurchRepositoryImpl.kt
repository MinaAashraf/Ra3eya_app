package com.mina.dev.ra3eya_app.data.repository

import androidx.lifecycle.LiveData
import com.mina.dev.ra3eya_app.data.local.datasource.ChurchLocalDataSource
import com.mina.dev.ra3eya_app.data.remote.ChurchRemoteDataSource
import com.mina.dev.ra3eya_app.domain.model.Church
import com.mina.dev.ra3eya_app.domain.model.ChurchCredentials
import com.mina.dev.ra3eya_app.domain.repository.ChurchRepository
import com.mina.dev.ra3eya_app.domain.util.Result
import com.mina.dev.ra3eya_app.domain.util.onFailure
import com.mina.dev.ra3eya_app.domain.util.onSuccess
import javax.inject.Inject

class ChurchRepositoryImpl @Inject constructor(
    private val churchRemoteDataSource: ChurchRemoteDataSource,
    private val churchLocalDataSource: ChurchLocalDataSource
) :
    ChurchRepository {

    override suspend fun addChurch(church: Church): Result<String> {
        return churchRemoteDataSource.addChurch(church).onSuccess {
            churchLocalDataSource.addChurch(church)
        }
    }

    override fun signIn(church: ChurchCredentials): LiveData<Church> =
        churchLocalDataSource.signIn(church.name, church.password)

    // it will be removed
    override suspend fun readChurch(churchId: String): Result<Church> {
        return churchRemoteDataSource.readChurch(churchId)
    }


    override suspend fun refreshChurches(): Result<List<Church>> {
        return churchRemoteDataSource.readAllChurches().onSuccess {
            churchLocalDataSource.addAllChurches(it)
        }
    }

    override fun readAllChurches(): LiveData<List<Church>> =
        churchLocalDataSource.readAllChurches()


}