package com.example.crud.model.dashboard

data class DashboardMainMenuResponse(
	val menus: List<MenusItem>? = null
)
data class MenusItem(
	val menuId: Int? = null,
	val menuTitle: String? = null,
	val menuImage: Int? = null
)

