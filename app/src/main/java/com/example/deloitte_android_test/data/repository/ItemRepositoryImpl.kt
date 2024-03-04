package com.example.deloitte_android_test.data.repository


import android.util.Log
import com.example.deloitte_android_test.data.database.dao.ItemDao
import com.example.deloitte_android_test.data.database.mapper.DbMapper
import com.example.deloitte_android_test.data.enums.ErrorModel
import com.example.deloitte_android_test.data.enums.ErrorType
import com.example.deloitte_android_test.data.network.client.ItemListClient
import com.example.deloitte_android_test.data.network.mapper.ApiMapper
import com.example.deloitte_android_test.domain.model.Item
import com.example.deloitte_android_test.domain.model.ItemListResult
import com.example.deloitte_android_test.domain.repository.ItemRepository
import com.example.deloitte_android_test.utils.DataHandler
import com.example.deloitte_android_test.utils.mapErrorModel
import com.example.deloitte_android_test.utils.mapErrorResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ItemRepositoryImpl @Inject constructor(
    private val itemListClient: ItemListClient,
    private val mapper: DbMapper,
    private val apiMapper: ApiMapper,
    private val dao: ItemDao
) : ItemRepository {

    override suspend fun getRemoteItemListResult(): DataHandler<ItemListResult> {
        return try {
            val response = itemListClient.getItemListResponse()
            if (response.isSuccessful) {
                response.body()?.let {
                    DataHandler.SUCCESS(apiMapper.mapApiItemListResponseToDomain(it))
                } ?: DataHandler.ERROR(error = ErrorModel(ErrorType.UNKNOWN))

            } else {
                DataHandler.ERROR(error = response.mapErrorResponse().mapErrorModel())
            }
        } catch (ex: Exception) {
            DataHandler.ERROR(error = ErrorModel(ErrorType.NETWORK))
        }
    }

    override suspend fun getLocalItemListResult(): DataHandler<List<Item>> {
        return try {
            DataHandler.SUCCESS(mapper.mapDbItemsToDomain(dao.getItems()))

        } catch (ex: Exception) {
            DataHandler.ERROR(error = ErrorModel(ErrorType.UNKNOWN))
        }
    }

    override suspend fun deleteItem(itemId: String) {
        try {
            dao.deleteItem(id = itemId)
        } catch (ex: Exception) {
            ex.message?.let { Log.e(tag, it) }
        }
    }

    override suspend fun getItemList(): DataHandler<Flow<List<Item>>> {
        return try {
            DataHandler.SUCCESS(dao.getListItems().map {
                mapper.mapDbItemsToDomain(it)
            })

        } catch (ex: Exception) {
            DataHandler.ERROR(error = ErrorModel(ErrorType.UNKNOWN))
        }
    }


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