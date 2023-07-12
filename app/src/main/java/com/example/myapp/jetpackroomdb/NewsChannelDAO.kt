package com.example.myapp.jetpackroomdb

import androidx.room.*

@Dao
interface NewsChannelDAO {
    @Query("SELECT * from news_channel_program_list")
    fun getAll(): List<NewsChannelEntity>

    @Query("SELECT * from news_channel_program_list where id = :id")
    fun getById(id: Int) : NewsChannelEntity?
    @Query("SELECT * from news_channel_program_list where channel_name=:channelName")
    fun getByChannelName(channelName:String) :List<NewsChannelEntity>
    @Insert
    suspend fun insert(item:NewsChannelEntity)

    @Update
    suspend fun update(item:NewsChannelEntity)

//    @Query("delete from news_channel_program_list where id=:id")
    @Delete
    suspend fun delete(item: NewsChannelEntity)

    @Query("DELETE FROM news_channel_program_list")
    suspend fun deleteAllTodos()
}