package com.example.tugasbesarpbp.models

import com.google.gson.annotations.SerializedName


class Bill (@SerializedName("userId") var userId:Int,
            @SerializedName("name") var name:String,
            @SerializedName("date") var date:String,
            @SerializedName("amount") var amount: Double){
    var id:Int? = null
}