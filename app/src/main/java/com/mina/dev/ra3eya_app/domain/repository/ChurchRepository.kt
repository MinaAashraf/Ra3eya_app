package com.mina.dev.ra3eya_app.domain.repository

import androidx.lifecycle.LiveData
import com.mina.dev.ra3eya_app.domain.model.Church
import com.mina.dev.ra3eya_app.domain.model.ChurchCredentials
import com.mina.dev.ra3eya_app.domain.util.Result

interface ChurchRepository {
    suspend fun addChurch (church: Church) : Result<String>

     fun signIn (church: ChurchCredentials) : LiveData<Church>

    suspend fun readChurch (churchId : String) : Result<Church>

    fun readAllChurches () : LiveData<List<Church>>

    suspend fun refreshChurches () : Result<List<Church>>
}