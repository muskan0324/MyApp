package com.example.myapp.jetpackroomdb.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.DeleteColumn
import androidx.room.Insert
import androidx.room.Query
import com.example.myapp.jetpackroomdb.entity.AccountEntity

@Dao
interface AccountDAO {
    @Insert
   suspend fun insert(accountEntity: AccountEntity)

    @Query("select password from user_details where mobile_no= :mobileNo")
    suspend fun getPassword(mobileNo:String) : String

    @Query("update user_details set password=:newPass where mobile_no=:mobileNo")
    suspend fun updatePassword(mobileNo: String,newPass:String)

    @Delete
    suspend fun deleteEntry(accountEntity: AccountEntity)
   @Query("delete from user_details")
    suspend fun deleteAll()

    @Query("select * from user_details where mobile_no=:mobileNo")
    suspend fun getByMobileNo(mobileNo: String) : AccountEntity
    @Query("select * from user_details where mobile_no=:mobileNo")
    suspend fun getListByMobileNo(mobileNo: String) : List<AccountEntity>

}