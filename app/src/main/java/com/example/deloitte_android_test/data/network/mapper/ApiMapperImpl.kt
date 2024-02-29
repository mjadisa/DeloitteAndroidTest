package com.example.deloitte_android_test.data.network.mapper

import com.example.deloitte_android_test.data.network.model.response.ItemListResponse
import com.example.deloitte_android_test.data.network.model.response.Products
import com.example.deloitte_android_test.domain.model.Item
import com.example.deloitte_android_test.domain.model.ItemListResult
import com.example.deloitte_android_test.utils.EMPTY_STRING
import javax.inject.Inject


class ApiMapperImpl @Inject constructor() : ApiMapper {
    override fun mapApiItemListResponseToDomain(itemListResponse: ItemListResponse): ItemListResult {
        return with(itemListResponse) {
            ItemListResult(
                items = products.map {
                    mapApiProductsToDomain(it)
                } as ArrayList<Item>

            )
        }
    }

    private fun mapApiProductsToDomain(products: Products): Item {
        return with(products) {
            Item(
                name = name ?: EMPTY_STRING,
                category = category ?: EMPTY_STRING,
                image = image ?: EMPTY_STRING,
                price = price ?: 0.0,
                stock = stock ?: 0,
                oldPrice = oldPrice ?: 0.0,
                productId = productId ?: EMPTY_STRING

            )
        }
    }


}
