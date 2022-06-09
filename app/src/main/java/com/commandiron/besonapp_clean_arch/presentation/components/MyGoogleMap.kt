package com.commandiron.besonapp_clean_arch.presentation.components

import android.content.Context
import android.graphics.Bitmap
import androidx.annotation.DrawableRes
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import com.commandiron.besonapp_clean_arch.R
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*
import kotlinx.coroutines.delay
import okhttp3.internal.wait

@Composable
fun MyGoogleMap(
    modifier: Modifier = Modifier,
    latLng: LatLng?
) {
    val cameraPositionState: CameraPositionState = rememberCameraPositionState{
        position = CameraPosition.fromLatLngZoom(latLng ?: LatLng(41.015137,28.979530), 4f)
    }
    val markerState = rememberMarkerState()
    var isMapLoaded by remember { mutableStateOf(false)}

    LaunchedEffect(key1 = latLng, isMapLoaded){
        if(isMapLoaded){
            delay(500)
            latLng?.let {
                cameraPositionState.animate(CameraUpdateFactory.newLatLngZoom(it, 10f))
                markerState.position = it
                markerState.showInfoWindow()
            } ?: markerState.hideInfoWindow()
        }
    }
    GoogleMap(
        modifier = modifier,
        cameraPositionState = cameraPositionState,
        uiSettings = MapUiSettings(
            myLocationButtonEnabled = true,
            mapToolbarEnabled = false,
            scrollGesturesEnabled = false,
            zoomControlsEnabled = false
        ),
        onMapLoaded = {
            isMapLoaded = true
        }
    ){
        MapMarker(
            context = LocalContext.current,
            state = markerState,
            iconResourceId = R.drawable.ic_map_maker
        )
    }
}

@Composable
fun MapMarker(
    context: Context,
    state: MarkerState,
    @DrawableRes iconResourceId: Int
) {
    val icon = bitmapDescriptorFromVector(
        context, iconResourceId
    )
    Marker(
        state = state,
        icon = icon
    )
}

fun bitmapDescriptorFromVector(
    context: Context,
    vectorResId: Int
): BitmapDescriptor? {

    // retrieve the actual drawable
    val drawable = ContextCompat.getDrawable(context, vectorResId) ?: return null
    drawable.setBounds(0, 0, drawable.intrinsicWidth, drawable.intrinsicHeight)
    val bm = Bitmap.createBitmap(
        drawable.intrinsicWidth,
        drawable.intrinsicHeight,
        Bitmap.Config.ARGB_8888
    )

    // draw it onto the bitmap
    val canvas = android.graphics.Canvas(bm)
    drawable.draw(canvas)
    return BitmapDescriptorFactory.fromBitmap(bm)
}