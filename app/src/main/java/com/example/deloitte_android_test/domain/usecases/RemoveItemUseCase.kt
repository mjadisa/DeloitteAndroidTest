package com.example.deloitte_android_test.domain.usecases

import com.example.deloitte_android_test.domain.repository.ItemRepository
import javax.inject.Inject


class RemoveItemUseCase @Inject constructor(
    private val repo: ItemRepository
) {
    suspend operator fun invoke(itemId: String) {
        return repo.deleteItem(itemId)
    }
}