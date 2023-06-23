package com.example.crud.model

data class User(
    val userList: List<Users>
){
    data class Users(
        val name: String,
        val phone: String,
        val email: String,
        val uid: String,
    )
}