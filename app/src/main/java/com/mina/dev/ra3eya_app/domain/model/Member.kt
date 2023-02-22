package com.mina.dev.ra3eya_app.domain.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "member_table")
data class Member(
    var idImage: String? = null,
    var name: String = "",
    var phone: String = "",
    var job: String = "",
    var familyName: String = "",
    var address: String = "",
    var relation: String = "",
    var spiritualFather: String = "",
    var homeId: String = "",
    var churchId: String = "",
) : Parcelable, VirtualParent {
    @PrimaryKey(autoGenerate = true)
    var key: Int? = null

}
