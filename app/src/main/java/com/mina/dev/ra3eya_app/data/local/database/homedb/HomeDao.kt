package com.mina.dev.ra3eya_app.data.local.database.homedb

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mina.dev.ra3eya_app.domain.model.Church
import com.mina.dev.ra3eya_app.domain.model.ChurchCredentials
import com.mina.dev.ra3eya_app.domain.model.Family
import com.mina.dev.ra3eya_app.domain.model.Home
import com.mina.dev.ra3eya_app.domain.util.Result

@Dao
interface HomeDao {

    @Query("Select * from home_table")
    fun readHomes() : LiveData<List<Home>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertHome(home:Home)

    @Query("DELETE FROM home_table")
    fun clearHomes ()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertHomes(homes:List<Home>)


    @Query("select * from home_table where homeId like :homeNameSubString and churchId = :churchId")
    fun searchHome(homeNameSubString: String, churchId : String) : LiveData<List<Home>>


}