package com.mina.dev.ra3eya_app.data.local.database.homedb

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mina.dev.ra3eya_app.domain.model.Home


@Database (entities = [Home::class], version = 1, exportSchema = false)
abstract class HomeDb : RoomDatabase() {
    abstract fun getDao () : HomeDao

}