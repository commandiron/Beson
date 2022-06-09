package com.commandiron.besonapp_clean_arch.domain.use_case

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import androidx.core.app.ActivityCompat
import com.commandiron.besonapp_clean_arch.core.Result
import com.commandiron.besonapp_clean_arch.core.Strings.LOCATION_PERMISSION_NOT_GRANTED
import com.google.android.gms.location.FusedLocationProviderClient
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class GetUserLastKnownLocation(
    private val fusedLocationClient: FusedLocationProviderClient
) {
    suspend operator fun invoke(): Flow<Result<Location?>> = callbackFlow{
        send(Result.Loading())
        val fineLocationPermission = ActivityCompat.checkSelfPermission(
            fusedLocationClient.applicationContext,
            Manifest.permission.ACCESS_FINE_LOCATION
        )
        val coarseLocationPermission = ActivityCompat.checkSelfPermission(
            fusedLocationClient.applicationContext,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
        if(fineLocationPermission == PackageManager.PERMISSION_GRANTED &&
            coarseLocationPermission == PackageManager.PERMISSION_GRANTED){
            fusedLocationClient.lastLocation.addOnCompleteListener { task ->
                if(task.isSuccessful){
                    trySend(Result.Success(task.result))
                }else{
                    trySend(Result.Error(task.exception))
                }
            }
        }else{
            trySend(Result.Error(Exception(LOCATION_PERMISSION_NOT_GRANTED)))
        }
        awaitClose {
            channel.close()
            cancel()
        }
    }
}