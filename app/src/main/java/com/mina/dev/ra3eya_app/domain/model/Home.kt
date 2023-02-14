package com.mina.dev.ra3eya_app.domain.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "home_table")
@Parcelize
data class Home(
    var name: String? = "",
    var churchId: String? = "",
    var familiesNo: Int? = 0,
    var location: MyLocation? = null,
    var addressLine : String? = null,
    var detailedAddress : String? = null,
    var families: List<FamilyNameId>? = null,
    var homeId : String = "",
    @PrimaryKey var key : Int? = null

) : Parcelable
