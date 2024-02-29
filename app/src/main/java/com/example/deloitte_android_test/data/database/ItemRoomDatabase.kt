package com.example.deloitte_android_test.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.deloitte_android_test.data.database.dao.ItemDao
import com.example.deloitte_android_test.data.database.entities.DbItem


@Database(entities = [DbItem::class], version = 1, exportSchema = false)
abstract class ItemRoomDatabase : RoomDatabase() {
    abstract fun itemDao(): ItemDao
}
