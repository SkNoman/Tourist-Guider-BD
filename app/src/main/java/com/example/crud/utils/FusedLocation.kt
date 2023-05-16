package com.example.crud.utils

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope

object FusedLocation {

    var lat: Double = 0.0
    var long: Double = 0.0

    @SuppressLint("LogNotTimber")

     fun getLocation(context: Context): Pair<Double, Double> {

                if (ActivityCompat.checkSelfPermission(
                        context,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    ) == PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(
                        context,
                        Manifest.permission.ACCESS_FINE_LOCATION
                    ) == PackageManager.PERMISSION_GRANTED
                ) {
                    val fusedLocationProviderClient: FusedLocationProviderClient =
                        LocationServices.getFusedLocationProviderClient(context)
                    fusedLocationProviderClient.lastLocation.addOnSuccessListener {
                        try {
                            Log.d("latLongFused", "Lat: ${it.latitude}, Long: ${it.longitude}")
                            lat = it.latitude
                            long = it.longitude
                        } catch (e: Exception) {
                            Log.d("latLongException", e.toString())
                        }
                    }
                }
        return lat to long
    }
}