package com.mina.dev.ra3eya_app.data.remote

import com.mina.dev.ra3eya_app.domain.model.Home
import com.mina.dev.ra3eya_app.domain.util.Result

interface HomeRemoteDataSource {
    suspend fun insertHome(home: Home): Result<String>
    suspend fun readHomes (churchId : String) : Result<List<Home>>

}