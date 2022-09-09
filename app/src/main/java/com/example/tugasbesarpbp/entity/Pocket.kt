package com.example.tugasbesarpbp.entity

class Pocket (var pocketName: String, var pocketBalance: Double){
    companion object{
        @JvmField
        val listOfPocket = mutableListOf(
            Pocket("Main Pocket", 255.0),
            Pocket("Locked Pocket", 500.0),
            Pocket("Savings Pocket", 800.0),
            Pocket("Investment Pocket", 700.0),
            Pocket("Retirement Pocket", 5.0),
            Pocket("Wedding Fund Pocket", 20.0),
            Pocket("School Fund Pocket", 689.0),
            Pocket("Social Fund Pocket", 111.0),
            Pocket("Holiday Fund Pocket", 28.0),
            Pocket("Social Gathering Pocket", 0.0)
        )
    }
}