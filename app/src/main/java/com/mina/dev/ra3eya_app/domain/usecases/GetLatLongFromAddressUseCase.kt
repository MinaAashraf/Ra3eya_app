package com.mina.dev.ra3eya_app.domain.usecases

import android.content.Context
import android.location.Address
import android.location.Geocoder
import com.google.android.gms.maps.model.LatLng
import dagger.hilt.android.qualifiers.ApplicationContext

class GetLatLongFromAddressUseCase () {
    fun execute( context: Context,  locationName:String) : LatLng{
        val geocoder = Geocoder(context)
        val address: MutableList<Address> = geocoder.getFromLocationName(locationName, 5)
        return LatLng(address[0].latitude, address[0].longitude)
    }
}