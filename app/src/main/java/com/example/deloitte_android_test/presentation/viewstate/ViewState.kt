package com.example.deloitte_android_test.presentation.viewstate

import com.example.deloitte_android_test.utils.EMPTY_STRING

data class ItemListViewState(
    var itemsViewState: ArrayList<ItemViewState> = arrayListOf()

)

data class ItemViewState(
    val name: String = EMPTY_STRING,
    val image: String = EMPTY_STRING,
    val price: Double = 0.0,
    val stock: Int = 0,
    val category: String = EMPTY_STRING,
    val oldPrice: Double = 0.0,
    val productId: String = EMPTY_STRING,
    var wishListStatus: Boolean = false,
    var cartStatus: Boolean = false,
    var quantity: Int = 1
)