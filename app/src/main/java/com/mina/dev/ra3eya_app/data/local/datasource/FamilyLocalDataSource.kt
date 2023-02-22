package com.mina.dev.ra3eya_app.data.local.datasource

import androidx.lifecycle.LiveData
import com.mina.dev.ra3eya_app.domain.model.Family

interface FamilyLocalDataSource {

    fun readAllFamiliesOfHome(homeId:String) : LiveData<List<Family>>
    suspend fun insertFamily(family: Family)
    suspend fun insertAllFamilies(families: List<Family>)

    fun readFamily (familyId : String,homeId: String) : LiveData<Family>
    fun readFamilies () : LiveData<List<Family>>
    fun searchFamily(familyNameSubString: String) : LiveData<List<Family>>

    suspend fun clearAllFamilies ()

}