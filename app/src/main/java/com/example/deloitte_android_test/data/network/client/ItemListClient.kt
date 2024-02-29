package com.example.deloitte_android_test.data.network.client


import com.example.deloitte_android_test.BuildConfig
import com.example.deloitte_android_test.data.network.model.response.ItemListResponse
import retrofit2.Response
import retrofit2.http.GET

interface ItemListClient {

    @GET(BuildConfig.ITEM_LIST_ENDPOINT)
    suspend fun getItemListResponse(): Response<ItemListResponse>
}
