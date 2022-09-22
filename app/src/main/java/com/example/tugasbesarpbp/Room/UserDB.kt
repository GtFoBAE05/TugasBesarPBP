package com.example.tugasbesarpbp.Room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import kotlinx.coroutines.internal.synchronized
import java.util.concurrent.locks.Lock

@Database(
    version=1,
    entities=[User::class]
)

abstract class UserDB:RoomDatabase() {
    abstract fun userDao():UserDao

    companion object{
        @Volatile private var instace: UserDB?=null
        private val LOCK = Any()

        operator fun invoke(context:Context)= instace ?: kotlin.synchronized(LOCK){
            instace?:buildDatabase(context).also{
                instace=it
            }
        }

        private fun buildDatabase(context: Context)= Room.databaseBuilder(
            context.applicationContext,
            UserDB::class.java,
            "user.db"
        ).build()
    }

}