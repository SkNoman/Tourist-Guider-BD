package com.example.crud.model.menu

data class PlaceListResponse(
	val placeList: List<PlaceListItem?>? = null
)

data class PlaceListItem(
	val placeId: Int? = null,
	val placeName: String? = null,
	val placeImage: String? = null,
	val placeHotelCount: String? = null
)

