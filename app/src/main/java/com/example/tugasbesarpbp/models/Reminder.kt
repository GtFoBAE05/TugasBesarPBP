package com.example.tugasbesarpbp.models

import com.google.gson.annotations.SerializedName

class Reminder (@SerializedName("userId") var userId:Int,
                @SerializedName("name") var name:String,
                @SerializedName("date") var date: String) {
    var id:Int? = null
}