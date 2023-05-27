package com.example.crud.utils

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log

object GoogleMaps {
    fun openGoogleMaps(context: Context, latitude: Double, longitude: Double) {
        Log.e("nlog-lat","Lat: $latitude, Long: $longitude")
        val uri = "https://www.google.com/maps/place/$latitude,$longitude"
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
        intent.setPackage("com.google.android.apps.maps")
        context.startActivity(intent)
    }
}