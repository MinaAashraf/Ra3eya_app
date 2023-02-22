package com.mina.dev.ra3eya_app.data.local.database.familydb

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mina.dev.ra3eya_app.domain.model.Church
import com.mina.dev.ra3eya_app.domain.model.ChurchCredentials
import com.mina.dev.ra3eya_app.domain.model.Family
import com.mina.dev.ra3eya_app.domain.util.Result

@Dao
interface FamilyDao {

    @Query("Select * from family_table where homeId = :homeId")
    fun readAllFamiliesOfHome(homeId: String): LiveData<List<Family>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFamily(family: Family)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllFamilies(families: List<Family>)


    @Query("select * from family_table where familyName =:familyNameSubString")
    fun searchFamily(familyNameSubString: String): LiveData<List<Family>>


    @Query("select * from family_table where  familyName=:familyName and homeId =:homeId")
    fun readFamily(familyName: String,homeId:String): LiveData<Family>

    @Query("select * from family_table")
    fun readFamilies(): LiveData<List<Family>>


    @Query("DELETE FROM family_table")
    suspend fun clearFamilies()


}