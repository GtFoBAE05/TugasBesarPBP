package com.example.tugasbesarpbp

class LoginInfo(var username: String, var password: String, var email:String, var date: String, var NoTelp: String) {

    companion object{
        @JvmField
        var listOfLogin = arrayOf(
            LoginInfo("admin", "admin", "admin", "admin", "admin")
        )
    }

}