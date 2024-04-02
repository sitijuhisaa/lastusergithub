package com.dicoding.usergithub.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [LikeUser::class], version = 1, exportSchema = false)
abstract class LikeDatabase : RoomDatabase() {
    abstract fun likeDao(): LikeDao

    companion object{
        @Volatile
        private var INSTANCE : LikeDatabase? = null

        @JvmStatic
        fun getDatabase (context: Context) : LikeDatabase{
            if (INSTANCE == null) {
                synchronized(LikeDatabase::class.java) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                        LikeDatabase::class.java, "favorite_database")
                        .build()
                }
            }
            return INSTANCE as LikeDatabase
        }
    }
}