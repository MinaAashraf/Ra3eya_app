package com.mina.dev.ra3eya_app.data.local.database.churchdb

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mina.dev.ra3eya_app.domain.model.Church
import com.mina.dev.ra3eya_app.domain.model.ChurchCredentials
import com.mina.dev.ra3eya_app.domain.util.Result

@Dao
interface ChurchDao {

    @Query("Select * from church_table where name=:churchName and password=:churchPassword")
    fun readChurch(churchName: String, churchPassword: String): LiveData<Church>

    @Query("Select * from church_table")
    fun readAllChurches(): LiveData<List<Church>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addChurch(church: Church)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addAllChurches(churches: List<Church>)


}