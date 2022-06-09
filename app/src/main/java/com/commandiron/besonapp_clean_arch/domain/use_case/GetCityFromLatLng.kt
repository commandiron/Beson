package com.commandiron.besonapp_clean_arch.domain.use_case

import android.location.Geocoder
import com.commandiron.besonapp_clean_arch.core.Result
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.util.*

class GetCityFromLatLng(
    private val fusedLocationClient: FusedLocationProviderClient
) {
    operator fun invoke(latLng: LatLng): String {
        val geocoder = Geocoder(fusedLocationClient.applicationContext, Locale.getDefault())
        val addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1)
        return addresses[0].adminArea
    }
}