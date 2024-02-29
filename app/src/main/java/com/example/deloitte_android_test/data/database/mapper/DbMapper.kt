package com.example.deloitte_android_test.data.database.mapper

import com.example.deloitte_android_test.data.database.entities.DbItem
import com.example.deloitte_android_test.domain.model.Item


interface DbMapper {

    fun mapDomainItemToDb(item: Item): DbItem

    fun mapDbItemToDomain(dbItem: DbItem): Item

}