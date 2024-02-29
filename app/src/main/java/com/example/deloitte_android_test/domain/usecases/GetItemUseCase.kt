package com.example.deloitte_android_test.domain.usecases

import com.example.deloitte_android_test.domain.model.Item
import com.example.deloitte_android_test.domain.repository.ItemRepository
import com.example.deloitte_android_test.utils.DataHandler
import javax.inject.Inject


class GetItemUseCase @Inject constructor(private val repo: ItemRepository) {
    suspend operator fun invoke(id: String): DataHandler<Item> {
        return repo.getItem(id)
    }
}
