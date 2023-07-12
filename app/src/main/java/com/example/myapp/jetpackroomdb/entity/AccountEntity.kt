package com.example.myapp.jetpackroomdb.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_details")
data class AccountEntity (
    @PrimaryKey(autoGenerate = true)
    var id: Long =0L,
    @ColumnInfo(name="name")
    val name:String,
    @ColumnInfo(name = "mobile_no")
    val mobileNo: String,
    @ColumnInfo(name="location")
    val location:String,
    @ColumnInfo(name = "password")
    var password: String,

    )