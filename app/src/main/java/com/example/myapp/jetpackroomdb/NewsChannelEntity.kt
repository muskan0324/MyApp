package com.example.myapp.jetpackroomdb


import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "news_channel_program_list")
data class NewsChannelEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Long =0L,
    @ColumnInfo(name = "channel_name")
    val channelName: String,
    @ColumnInfo(name = "program_name")
    var programName: String,
    @ColumnInfo(name="telecast_time")
    var telecastTime:String
)