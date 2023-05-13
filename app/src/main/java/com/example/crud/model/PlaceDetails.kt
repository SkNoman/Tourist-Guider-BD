package com.example.crud.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class PlaceDetails (

    val placeName: String,
    val placeDetails: String,
    val placeDistrict: String,
    val lat: Double,
    val long: Double,
    val placeImage: String
):Parcelable


