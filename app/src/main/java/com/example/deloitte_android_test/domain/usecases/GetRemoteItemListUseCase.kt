package com.example.deloitte_android_test.domain.usecases

import com.example.deloitte_android_test.domain.model.ItemListResult
import com.example.deloitte_android_test.domain.repository.ItemRepository
import com.example.deloitte_android_test.utils.DataHandler
import javax.inject.Inject

class GetRemoteItemListUseCase @Inject constructor(private val repo: ItemRepository) {
    suspend operator fun invoke(): DataHandler<ItemListResult> {
        return repo.getRemoteItemListResult()
    }
}
