package com.example.deloitte_android_test.domain.repository


import com.example.deloitte_android_test.domain.model.Item
import com.example.deloitte_android_test.domain.model.ItemListResult
import com.example.deloitte_android_test.utils.DataHandler
import kotlinx.coroutines.flow.Flow

interface ItemRepository {

    suspend fun getItem(id: String): DataHandler<Item>
    suspend fun insertItem(item: Item)

    suspend fun getRemoteItemListResult(): DataHandler<ItemListResult>
    suspend fun getLocalItemListResult(): DataHandler<List<Item>>
    suspend fun deleteItem(itemId: String)
    suspend fun getItemList(): DataHandler<Flow<List<Item>>>


}