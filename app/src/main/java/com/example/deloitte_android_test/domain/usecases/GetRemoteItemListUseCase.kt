package com.example.deloitte_android_test.domain.usecases

import com.example.deloitte_android_test.domain.model.ItemListResult
import com.example.deloitte_android_test.domain.repository.ItemListRepository
import com.example.deloitte_android_test.utils.DataHandler
import javax.inject.Inject

class GetRemoteItemListUseCase @Inject constructor(private val repo: ItemListRepository) {
    suspend operator fun invoke(): DataHandler<ItemListResult> {
        return repo.getItemListResult()
    }
}
