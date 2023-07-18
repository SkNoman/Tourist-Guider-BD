package com.example.crud.admin.model

class PlaceName {
    var name: String? = null
    var placeDetails: PlaceDetails? = null

    constructor() {}

    constructor(name: String, placeDetails: PlaceDetails) {
        this.name = name
        this.placeDetails = placeDetails
    }
}
