package com.mina.dev.ra3eya_app.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "family_table")
data class Family(
     val familyName :String = "",
     val churchId :String = "",
     val homeId :String = "",
     val floorNum : Int = 0,
     val familyAddress : String = "",
     val persons : List<MemberNameId>? = null,
     @PrimaryKey var key : Int? = null

)
