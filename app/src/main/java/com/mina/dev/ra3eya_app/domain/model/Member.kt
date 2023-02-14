package com.mina.dev.ra3eya_app.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "member_table")
data class Member(
    var idImage: String? = null,
    val name: String = "",
    var phone: String = "",
    var job: String = "",
    val familyName : String = "",
    val address : String = "",
    val relation: String = "",
    var spiritualFather : String = "",
    var homeId : String = "",
    var familyId : String = "",
    var churchId : String = "",
    @PrimaryKey var key : Int? = null
)
