package com.example.deloitte_android_test.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.deloitte_android_test.data.database.entities.DbItem

@Dao
interface ItemDao {
    @Query("SELECT * FROM item_table WHERE productId=:id")
    suspend fun getItem(id: String): DbItem

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(item: DbItem)

    @Query("DELETE FROM item_table")
    suspend fun deleteAll()
}
