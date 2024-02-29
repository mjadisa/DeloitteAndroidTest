package com.example.deloitte_android_test.domain.repository


import com.example.deloitte_android_test.domain.model.Item
import com.example.deloitte_android_test.utils.DataHandler

interface ItemRepository {

    suspend fun getItem(id: String): DataHandler<Item>
    suspend fun insertItem(item: Item)

}