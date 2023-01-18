package com.mina.dev.ra3eya_app.domain.usecases

import com.mina.dev.ra3eya_app.data.remote.ChurchRemoteDataSource
import com.mina.dev.ra3eya_app.domain.model.Church
import com.mina.dev.ra3eya_app.domain.util.Result
import javax.inject.Inject

class ReadChurchUseCase @Inject constructor(private val churchRemoteDataSource: ChurchRemoteDataSource) {
   suspend fun execute (churchId:String): Result<Church> {
       return churchRemoteDataSource.readChurch(churchId)
    }
}