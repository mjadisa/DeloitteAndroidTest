package com.example.deloitte_android_test.data.repository


import android.util.Log
import com.example.deloitte_android_test.data.database.dao.ItemDao
import com.example.deloitte_android_test.data.database.mapper.DbMapper
import com.example.deloitte_android_test.data.enums.ErrorModel
import com.example.deloitte_android_test.data.enums.ErrorType
import com.example.deloitte_android_test.domain.model.Item
import com.example.deloitte_android_test.domain.repository.ItemRepository
import com.example.deloitte_android_test.utils.DataHandler
import javax.inject.Inject

class ItemRepositoryImpl @Inject constructor(
    private val mapper: DbMapper,
    private val dao: ItemDao
) : ItemRepository {
    override suspend fun getItem(id: String): DataHandler<Item> {
        return try {
            DataHandler.SUCCESS(mapper.mapDbItemToDomain(dao.getItem(id)))

        } catch (ex: Exception) {
            DataHandler.ERROR(error = ErrorModel(ErrorType.UNKNOWN))
        }
    }

    override suspend fun insertItem(item: Item) {
        try {
            dao.insert(mapper.mapDomainItemToDb(item))
        } catch (ex: Exception) {
            ex.message?.let { Log.e(tag, it) }
        }
    }

    companion object {
        val tag: String = ItemRepositoryImpl::class.java.simpleName
    }
}