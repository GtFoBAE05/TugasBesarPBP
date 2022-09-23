package com.example.tugasbesarpbp.pocketRoom

import androidx.room.*

@Dao
interface PocketDao {
    @Insert
    suspend fun addPocket(pocket: pocket)

    @Update
    suspend fun updatePocket(pocket: pocket)

    @Delete
    suspend fun deletePocket(pocket: pocket)

    @Query("SELECT * FROM pocket WHERE userId= :x")
    suspend fun getPocket(x:Int): List<pocket>

}