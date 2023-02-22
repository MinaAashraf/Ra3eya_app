package com.mina.dev.ra3eya_app.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MemberNameId(
    var name : String = "",
    var id : String = ""
):Parcelable
