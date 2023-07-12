package com.example.myapp.jetpackroomdb

class NewsChannelHelper {

    suspend fun insertItem(item: NewsChannelEntity, dbDAOInstance: NewsChannelDAO) {
        dbDAOInstance.insert(item)

    }
    suspend fun updateItem(item: NewsChannelEntity, dbDAOInstance: NewsChannelDAO) {
        dbDAOInstance.update(item)

    }
    suspend fun deleteItem(id:Long, dbDAOInstance: NewsChannelDAO) {
        dbDAOInstance.delete(NewsChannelEntity(id=id, channelName = "", programName = "", telecastTime = ""))

    }

}