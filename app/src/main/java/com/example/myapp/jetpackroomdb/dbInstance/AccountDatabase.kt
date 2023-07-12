package com.example.myapp.jetpackroomdb.dbInstance

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.myapp.jetpackroomdb.dao.AccountDAO
import com.example.myapp.jetpackroomdb.entity.AccountEntity

@Database(entities = [AccountEntity::class], version = 2)
abstract class AccountDatabase : RoomDatabase() {
    abstract fun accountDAO():AccountDAO

    companion object {
        private var INSTANCE: AccountDatabase? = null
        fun getInstance(context: Context): AccountDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        AccountDatabase::class.java,
                        "mainp_db"
                    ).fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}