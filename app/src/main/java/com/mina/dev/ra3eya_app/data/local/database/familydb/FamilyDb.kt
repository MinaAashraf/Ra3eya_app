package com.mina.dev.ra3eya_app.data.local.database.familydb

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mina.dev.ra3eya_app.domain.model.Church
import com.mina.dev.ra3eya_app.domain.model.Family


@Database (entities = [Family::class], version = 1, exportSchema = false)
abstract class FamilyDb : RoomDatabase() {
    abstract fun getDao () : FamilyDao

}