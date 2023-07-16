package com.example.crud.admin.model

import android.provider.ContactsContract.CommonDataKinds.Email

class Admin {
    var email: String? = null
    var pass: String? = null

    constructor(){}

    constructor(email: String,pass:String){
        this.email = email
        this.pass = pass

    }
}
