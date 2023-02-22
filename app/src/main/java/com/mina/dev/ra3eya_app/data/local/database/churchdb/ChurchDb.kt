package com.mina.dev.ra3eya_app.data.local.database.churchdb

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.mina.dev.ra3eya_app.data.local.database.Converters
import com.mina.dev.ra3eya_app.domain.model.Church

@TypeConverters(Converters::class)
@Database (entities = [Church::class], version = 5, exportSchema = false)
abstract class ChurchDb : RoomDatabase() {
    abstract fun getDao () : ChurchDao

}