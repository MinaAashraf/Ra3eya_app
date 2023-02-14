package com.mina.dev.ra3eya_app.domain.repository

import androidx.lifecycle.LiveData
import com.mina.dev.ra3eya_app.domain.model.Home
import com.mina.dev.ra3eya_app.domain.util.Result

interface HomeRepository {
    suspend fun insertHome (home: Home): Result<String>

    suspend fun refreshHomes (churchId: String) : Result<List<Home>>

    fun readHomes () : LiveData<List<Home>>
}