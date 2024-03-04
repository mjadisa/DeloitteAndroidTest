package com.example.deloitte_android_test.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "item_table")
data class DbItem(
    @PrimaryKey var productId: String,
    var name: String? = null,
    var image: String? = null,
    var price: Double? = null,
    var stock: Int? = null,
    var category: String? = null,
    var oldPrice: Double? = null,
    var wishListStatus: Boolean = false,
    var cartStatus: Boolean = false,
    var quantity: Int = 1
)

