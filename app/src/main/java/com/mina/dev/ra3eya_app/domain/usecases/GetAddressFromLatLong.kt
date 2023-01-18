package com.mina.dev.ra3eya_app.domain.usecases

import android.content.Context
import android.location.Address
import android.location.Geocoder
import com.google.android.gms.maps.model.LatLng

class GetAddressFromLatLongUseCase {
    fun execute(context: Context, latLng : LatLng) : String {
        val geocoder = Geocoder(context)
        val address: MutableList<Address> = geocoder.getFromLocation(latLng.latitude,latLng.longitude,5)
        return address[0].getAddressLine(0)
    }
}