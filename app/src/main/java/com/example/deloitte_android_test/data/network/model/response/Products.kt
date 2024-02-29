package com.example.deloitte_android_test.data.network.model.response

import com.google.gson.annotations.SerializedName


data class Products(

    @SerializedName("name") var name: String? = null,
    @SerializedName("image") var image: String? = null,
    @SerializedName("price") var price: Double? = null,
    @SerializedName("stock") var stock: Int? = null,
    @SerializedName("category") var category: String? = null,
    @SerializedName("oldPrice") var oldPrice: Double? = null,
    @SerializedName("productId") var productId: String? = null

)