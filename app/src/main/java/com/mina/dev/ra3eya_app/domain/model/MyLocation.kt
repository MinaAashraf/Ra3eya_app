package com.mina.dev.ra3eya_app.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class MyLocation (
     var latitude: Double = 0.0,  var longitude: Double= 0.0
) : Parcelable
