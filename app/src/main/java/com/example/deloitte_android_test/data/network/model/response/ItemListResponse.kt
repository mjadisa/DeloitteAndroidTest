package com.example.deloitte_android_test.data.network.model.response

import com.google.gson.annotations.SerializedName


data class ItemListResponse(

    @SerializedName("products") var products: ArrayList<Products> = arrayListOf()

)