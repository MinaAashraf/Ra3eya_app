package com.mina.dev.ra3eya_app.domain.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "church_table")
@Parcelize
data class Church (
     var name: String = "",
     var password: String = "",
     var id: String = "",
     var location: MyLocation? = null,
     var addressLine : String? = null,
 //    var homes: MutableList<Home> = mutableListOf()
) : Parcelable
{
     @PrimaryKey(autoGenerate = true) var key : Int? = null

}