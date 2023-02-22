package com.mina.dev.ra3eya_app.data.local.database

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.mina.dev.ra3eya_app.domain.model.MyLocation

class Converters {
    @TypeConverter
    fun fromLocationObject(location: MyLocation): String = Gson().toJson(location)

    @TypeConverter
    fun toLocationObject(json: String): MyLocation = Gson().fromJson(json, MyLocation::class.java)
}