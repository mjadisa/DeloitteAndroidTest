package com.example.deloitte_android_test.data.database.mapper


import com.example.deloitte_android_test.data.database.entities.DbItem
import com.example.deloitte_android_test.domain.model.Item
import com.example.deloitte_android_test.utils.EMPTY_STRING
import javax.inject.Inject


class DbMapperImpl @Inject constructor() : DbMapper {


    override fun mapDomainItemToDb(item: Item): DbItem {
        return with(item) {
            DbItem(
                name = name,
                category = category,
                image = image,
                price = price,
                stock = stock,
                oldPrice = oldPrice,
                productId = productId

            )
        }
    }

    override fun mapDbItemToDomain(dbItem: DbItem): Item {
        return with(dbItem) {
            Item(
                name = name ?: EMPTY_STRING,
                category = category ?: EMPTY_STRING,
                image = image ?: EMPTY_STRING,
                price = price ?: 0.0,
                stock = stock ?: 0,
                oldPrice = oldPrice ?: 0.0,
                productId = productId

            )
        }
    }


}
