package com.example.crud.admin.model

class PlaceDetails{
    var placeKey: String? =null
    var nameEn: String? = null
    var nameBn: String? = null
    var districtEn: String? = null
    var districtBn: String? = null
    var divisionEn: String? = null
    var divisionBn: String? = null
    var imageLink: String? = null
    var lat: Double? = null
    var long: Double? = null
    var detailsEn: String? = null
    var detailsBn: String? = null

    constructor(){}

    constructor(placeKey: String,nameEn:String,nameBn:String,
                districtEn:String,districtBn:String,
                divisionEn:String,  divisionBn:String,
                imageLink: String,lat:Double, long: Double,
                detailsEn:String,detailsBn:String){
        this.placeKey = placeKey
        this.nameEn = nameEn
        this.nameBn = nameBn
        this.districtEn = districtEn
        this.districtBn = districtBn
        this.divisionEn = divisionEn
        this.divisionBn = divisionBn
        this.imageLink = imageLink
        this.lat = lat
        this.long = long
        this.detailsEn = detailsEn
        this.detailsBn = detailsBn
    }
}