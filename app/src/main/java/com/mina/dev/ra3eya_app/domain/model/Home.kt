package com.mina.dev.ra3eya_app.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Home(
    var name: String? = "",
    var churchId: String? = "",
    var familiesNo: Int? = 0,
    var location: MyLocation? = null,
    var addressLine : String? = null,
    var families: List<String>? = null
) : Parcelable