package com.mina.dev.ra3eya_app.data.remote

import com.mina.dev.ra3eya_app.domain.model.Church
import com.mina.dev.ra3eya_app.domain.model.ChurchCredentials
import com.mina.dev.ra3eya_app.domain.model.Family
import com.mina.dev.ra3eya_app.domain.model.Home
import com.mina.dev.ra3eya_app.domain.util.Result

interface ChurchRemoteDataSource {

    suspend fun readChurch (churchId : String) : Result<Church>

    suspend fun readAllChurches () : Result<List<Church>>

    suspend fun addChurch (church: Church): Result<String>

    suspend fun signIn (church: ChurchCredentials): Result<Church>


}