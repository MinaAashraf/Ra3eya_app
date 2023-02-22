package com.mina.dev.ra3eya_app.data.local.database.memberdb

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mina.dev.ra3eya_app.domain.model.Member


@Database (entities = [Member::class], version = 2, exportSchema = false)
abstract class MemberDb : RoomDatabase() {
    abstract fun getDao () : MemberDao

}