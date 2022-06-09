package com.commandiron.besonapp_clean_arch.domain.use_case

import android.location.Location
import com.google.android.gms.maps.model.LatLng

class GetLatLngFromLocation {
    operator fun invoke(location: Location): LatLng {
        return LatLng(location.latitude, location.longitude)
    }
}