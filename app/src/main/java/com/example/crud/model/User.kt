package com.example.crud.model



 class Users{
        var name: String?= null
        var phone: String?= null
        var email: String? =null
        var uid: String? = null

    constructor(){}

constructor(name: String?,phone:String?,email: String?,uid:String?){
    this.name = name
    this.phone = phone
    this.email = email
    this.uid =uid
}
}