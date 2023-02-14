package com.mina.dev.ra3eya_app.data.local.database.churchdb

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mina.dev.ra3eya_app.domain.model.Church


@Database (entities = [Church::class], version = 1, exportSchema = false)
abstract class ChurchDb : RoomDatabase() {
    abstract fun getDao () : ChurchDao

}