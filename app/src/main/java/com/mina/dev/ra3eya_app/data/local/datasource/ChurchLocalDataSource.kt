package com.mina.dev.ra3eya_app.data.local.datasource

import androidx.lifecycle.LiveData

import com.mina.dev.ra3eya_app.domain.model.Church
import com.mina.dev.ra3eya_app.domain.util.Result

interface ChurchLocalDataSource {
    fun readChurch(churchName: String,churchPassword: String): LiveData<Church>

    fun readAllChurches(): LiveData<List<Church>>

    suspend fun addChurch(church: Church)

    suspend fun addAllChurches(churches: List<Church>)

    fun signIn (churchName : String, churchPassword: String) : LiveData<Church>



}