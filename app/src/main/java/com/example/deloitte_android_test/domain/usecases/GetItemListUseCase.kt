package com.example.deloitte_android_test.domain.usecases

import com.example.deloitte_android_test.domain.model.Item
import com.example.deloitte_android_test.domain.repository.ItemRepository
import com.example.deloitte_android_test.utils.DataHandler
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class GetItemListUseCase @Inject constructor(private val repo: ItemRepository) {
    suspend operator fun invoke(): DataHandler<Flow<List<Item>>> {
        return repo.getItemList()
    }
}