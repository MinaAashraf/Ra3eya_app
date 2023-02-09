package com.mina.dev.ra3eya_app.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class FamilyNameId(
     val name : String = "",
     val id : String = "",
     val floorNum : Int = 0

) : Parcelable
