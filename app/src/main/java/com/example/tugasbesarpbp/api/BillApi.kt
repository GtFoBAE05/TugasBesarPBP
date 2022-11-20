package com.example.tugasbesarpbp.api

class BillApi {
    companion object{
        val BASE_URL = "http://192.168.0.120:8080/TugasBesarPBP/public/"

        val GET_ALL_URL = BASE_URL + "bill/"
        val GET_BY_ID_URL = BASE_URL + "bill/"
        val ADD_URL = BASE_URL + "bill"
        val UPDATE_URL = BASE_URL + "bill/"
        val DELETE_URL = BASE_URL + "bill/"
    }
}