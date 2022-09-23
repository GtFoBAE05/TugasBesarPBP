package com.example.tugasbesarpbp.pocketRoom

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class pocket(
    @PrimaryKey(autoGenerate = true)
    val id:Int,
    var userId:Int,
    val pocketName:String,
    val pocketBalance:Double
)