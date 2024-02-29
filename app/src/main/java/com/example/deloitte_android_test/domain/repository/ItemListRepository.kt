package com.example.deloitte_android_test.domain.repository

import com.example.deloitte_android_test.domain.model.ItemListResult
import com.example.deloitte_android_test.utils.DataHandler


interface ItemListRepository {

    suspend fun getItemListResult(): DataHandler<ItemListResult>

}