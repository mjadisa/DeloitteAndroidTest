package com.example.deloitte_android_test.data.network.mapper

import com.example.deloitte_android_test.data.network.model.response.ItemListResponse
import com.example.deloitte_android_test.domain.model.ItemListResult


interface ApiMapper {

    fun mapApiItemListResponseToDomain(itemListResponse: ItemListResponse): ItemListResult

}


