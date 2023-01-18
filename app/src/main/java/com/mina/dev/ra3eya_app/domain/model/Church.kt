package com.mina.dev.ra3eya_app.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Church (
     var name: String = "",
     var password: String = "",
     var id: String = "",
     var location: MyLocation? = null,
     var addressLine : String? = null,
     var homes: MutableList<Home> = mutableListOf()
) : Parcelable