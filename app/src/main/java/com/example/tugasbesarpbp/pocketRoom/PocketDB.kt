package com.example.tugasbesarpbp.pocketRoom

import android.content.Context
import androidx.room.*
import com.example.tugasbesarpbp.Room.User
import com.example.tugasbesarpbp.Room.UserDao

@Database(
    version=1,
    entities=[pocket::class]
)

abstract class PocketDB: RoomDatabase() {
    abstract fun pocketDao():PocketDao

    companion object{
        @Volatile private var instace: PocketDB?=null
        private val LOCK = Any()

        operator fun invoke(context: Context)= instace ?: kotlin.synchronized(LOCK){
            instace?:buildDatabase(context).also{
                instace=it
            }
        }

        private fun buildDatabase(context: Context)= Room.databaseBuilder(
            context.applicationContext,
            PocketDB::class.java,
            "pocket.db"
        ).build()
    }

}