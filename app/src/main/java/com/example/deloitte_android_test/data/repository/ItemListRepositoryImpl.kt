package com.example.deloitte_android_test.data.repository


import com.example.deloitte_android_test.data.enums.ErrorModel
import com.example.deloitte_android_test.data.enums.ErrorType
import com.example.deloitte_android_test.data.network.client.ItemListClient
import com.example.deloitte_android_test.data.network.mapper.ApiMapper
import com.example.deloitte_android_test.domain.model.ItemListResult
import com.example.deloitte_android_test.domain.repository.ItemListRepository
import com.example.deloitte_android_test.utils.DataHandler
import com.example.deloitte_android_test.utils.mapErrorModel
import com.example.deloitte_android_test.utils.mapErrorResponse
import javax.inject.Inject

class ItemListRepositoryImpl @Inject constructor(
    private val itemListClient: ItemListClient,
    private val apiMapper: ApiMapper,
) : ItemListRepository {
    override suspend fun getItemListResult(): DataHandler<ItemListResult> {
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

}