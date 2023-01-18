package com.mina.dev.ra3eya_app.domain.usecases

import android.content.Context
import com.google.android.gms.location.FusedLocationProviderClient
import javax.inject.Inject

class GetCurrentLocationUseCase @Inject constructor(private val fusedLocationProviderClient: FusedLocationProviderClient) {
    fun execute (context: Context){
    }
}