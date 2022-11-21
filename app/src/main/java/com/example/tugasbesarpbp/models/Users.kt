package com.example.tugasbesarpbp.models

import com.google.gson.annotations.SerializedName

class Users (@SerializedName("username") var username:String,
             @SerializedName("password") var password:String,
             @SerializedName("email") var email:String,
             @SerializedName("date") var date:String,
             @SerializedName("noTelp") var noTelp:String){
    var id: Int?=null
}