package com.example.deloitte_android_test.domain.model


data class ItemListResult(
    var items: ArrayList<Item> = arrayListOf()

)

data class Item(
    val name: String,
    val image: String,
    val price: Double,
    val stock: Int,
    val category: String,
    val oldPrice: Double,
    val productId: String
)