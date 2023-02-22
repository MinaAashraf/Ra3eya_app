package com.mina.dev.ra3eya_app.domain.repository

import android.content.Context
import androidx.lifecycle.LiveData
import com.mina.dev.ra3eya_app.domain.model.Family
import com.mina.dev.ra3eya_app.domain.model.FamilyNameId
import com.mina.dev.ra3eya_app.domain.util.Result

interface FamilyRepository {
    suspend fun addFamily(family: Family): Result<FamilyNameId>
    fun readFamily(
        familyId: String,
        homeId: String
    ): LiveData<Family>

    fun readFamilies(): LiveData<List<Family>>

    suspend fun refreshAllFamilies(churchId: String)
    suspend fun searchFamily(familyName: String): LiveData<List<Family>>

    fun readFamiliesOfHome(homeId: String) : LiveData<List<Family>>

}