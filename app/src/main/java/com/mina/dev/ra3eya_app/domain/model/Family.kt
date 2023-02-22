package com.mina.dev.ra3eya_app.domain.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "family_table")
@Parcelize
data class Family(
     var familyName :String = "",
     var churchId :String = "",
     var homeId :String = "",
     var floorNum : Int = 0,
     var familyAddress : String = "",
     @Ignore
     var persons : List<MemberNameId>? = null,

):Parcelable, VirtualParent{
     @PrimaryKey(autoGenerate = true)  var key : Int? = null

}
