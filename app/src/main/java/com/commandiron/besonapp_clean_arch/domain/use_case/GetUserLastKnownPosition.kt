package com.commandiron.besonapp_clean_arch.domain.use_case

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import androidx.core.app.ActivityCompat
import com.commandiron.besonapp_clean_arch.core.Result
import com.google.android.gms.location.FusedLocationProviderClient
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class GetUserLastKnownPosition(
    private val fusedLocationClient: FusedLocationProviderClient
) {
    suspend operator fun invoke(): Flow<Result<Location>> = callbackFlow{
        send(Result.Loading())
        fusedLocationClient.lastLocation.addOnCompleteListener() { task ->
            if (ActivityCompat.checkSelfPermission(fusedLocationClient.applicationContext,
                    Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                    fusedLocationClient.applicationContext,
                    Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
            ) {}
            if(task.isSuccessful){
                trySend(Result.Success(task.result))
            }else{
                trySend(Result.Error(task.exception))
            }
        }
        awaitClose {
            channel.close()
            cancel()
        }
    }
}