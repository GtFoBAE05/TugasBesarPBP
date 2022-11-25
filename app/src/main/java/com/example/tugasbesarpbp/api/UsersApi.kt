package com.example.tugasbesarpbp.api

class UsersApi {
    companion object{
        val BASE_URL = "http://192.168.1.101:8080/TugasBesarPBP/public/"

        val GET_ALL_URL = BASE_URL + "users/"
        val GET_BY_ID_URL = BASE_URL + "users/"
        val ADD_URL = BASE_URL + "users"
        val UPDATE_URL = BASE_URL + "users/"
    }

}