package com.example.tugasbesarpbp.Room

import androidx.room.*

@Dao
interface UserDao {
    @Insert
    suspend fun addUser(user: User)

    @Update
    suspend fun updateUser(user: User)

    @Query("SElECT * FROM user")
    suspend fun getUsers(): List<User>

    @Query("SELECT * FROM user WHERE username like :Username AND password like :Password")
    suspend fun checkUser(Username:String, Password: String): List<User>

    @Query("SELECT * FROM user where id= :userId")
    suspend fun getUserById(userId:Int): List<User>

}