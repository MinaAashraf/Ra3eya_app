package com.mina.dev.ra3eya_app.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CollectionsKeys(
    val churchKey: String? = null,
    val familyKey: String? = null,
    val homeKey: String? = null,
    val memberKey: String? = null,

) : Parcelable
