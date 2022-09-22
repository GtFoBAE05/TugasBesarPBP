package com.example.tugasbesarpbp.entity

class LoginInfo(var username: String, var password: String, var email:String, var date: String, var NoTelp: String) {

    companion object{
        @JvmField
        val listOfLogin = mutableListOf(
            LoginInfo("admin", "admin", "admin@gmail.com", "10/9/2022", "0811781"),
        )
    }
}