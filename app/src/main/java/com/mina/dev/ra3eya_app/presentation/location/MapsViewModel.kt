package com.mina.dev.ra3eya_app.presentation.location

import android.content.Context
import android.location.Address
import androidx.lifecycle.ViewModel
import com.google.android.gms.maps.model.LatLng
import com.mina.dev.ra3eya_app.domain.usecases.GetAddressFromLatLongUseCase
import com.mina.dev.ra3eya_app.domain.usecases.GetLatLongFromAddressUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MapsViewModel @Inject constructor(
    private val getLatLongFromAddressUseCase: GetLatLongFromAddressUseCase,
    private val getAddressFromLatLngUseCase: GetAddressFromLatLongUseCase
) : ViewModel() {

    fun getLatLongFromAddress(context: Context, address: String): LatLng {
        return getLatLongFromAddressUseCase.execute(context, address)
    }

    fun getAddressFromLatLng(context: Context, latLng: LatLng) : String {
        return getAddressFromLatLngUseCase.execute(context, latLng)
    }


}