package com.example.tugasbesarpbp.models

import com.google.gson.annotations.SerializedName

class Pocket(@SerializedName("userId") var userId:Int,
             @SerializedName("name") var name:String,
             @SerializedName("balance") var balance: Double) {
    var id:Int? = null
}