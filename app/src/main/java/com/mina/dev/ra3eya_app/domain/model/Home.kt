package com.mina.dev.ra3eya_app.domain.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import com.mina.dev.ra3eya_app.data.local.database.Converters
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
    @Ignore
    var families: List<FamilyNameId>? = null,
    var homeId : String = "",
    @PrimaryKey var key : Int? = null

) : Parcelable, VirtualParent
