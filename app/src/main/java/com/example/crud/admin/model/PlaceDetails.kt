package com.example.crud.admin.model

class PlaceDetails{
    var name: String? = null
    var district: String? = null
    var division: String? = null
    var imageLink: String? = null
    var lat: Double? = null
    var long: Double? = null
    var details: String? = null

    constructor(){}

    constructor(name:String,district:String,division:String,imageLink: String,lat:Double,
                long: Double, details:String){
        this.name = name
        this.district = district
        this.division = division
        this.imageLink = imageLink
        this.lat = lat
        this.long = long
        this.details = details
    }
}