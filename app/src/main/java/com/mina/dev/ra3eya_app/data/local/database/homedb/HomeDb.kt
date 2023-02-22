package com.mina.dev.ra3eya_app.data.local.database.homedb

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.mina.dev.ra3eya_app.data.local.database.Converters
import com.mina.dev.ra3eya_app.domain.model.Home

@TypeConverters(Converters::class)
@Database (entities = [Home::class], version = 1, exportSchema = false)
abstract class HomeDb : RoomDatabase() {
    abstract fun getDao () : HomeDao

}