package com.example.tugasbesarpbp

class LoginInfo(var username: String, var password: String, var email:String, var date: String, var NoTelp: String) {

    companion object{
        @JvmField
        val listOfLogin = mutableListOf(
            LoginInfo("admin", "admin", "admin", "admin", "admin"),
            LoginInfo("budi", "budi", "budi", "budi", "budi"),
        )
//        val listOfLogin = arrayOf(
//            LoginInfo("admin", "admin", "admin", "admin", "admin")
//        )
    }

}