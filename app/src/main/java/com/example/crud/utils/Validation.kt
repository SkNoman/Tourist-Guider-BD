package com.example.crud.utils

import java.util.regex.Pattern

object Validation {
    fun emailValidation(email:String):Boolean{
        val emailRegex = "^[A-Za-z](.*)([@]{1})(.{1,})(\\.)(.{1,})"

        val pattern = Pattern.compile(emailRegex)
        val matcher = pattern.matcher(email)

        return matcher.matches()
    }
}