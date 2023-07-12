package com.example.myapp.jetpackroomdb

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [NewsChannelEntity::class], version = 1)
abstract class NewsChannelDatabase : RoomDatabase() {
    abstract fun newschannelDAO():NewsChannelDAO

    companion object {
        private var INSTANCE: NewsChannelDatabase? = null
        fun getInstance(context: Context): NewsChannelDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        NewsChannelDatabase::class.java,
                        "main_db"
                    ).fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}