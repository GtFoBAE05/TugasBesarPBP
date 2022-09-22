package com.example.tugasbesarpbp.Room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User (
    @PrimaryKey(autoGenerate = true) val id:Int,
    var username: String,
    var password: String,
    var email:String,
    var date: String,
    var NoTelp: String
    )