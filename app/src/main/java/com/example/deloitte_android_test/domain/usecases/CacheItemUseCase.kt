package com.example.deloitte_android_test.domain.usecases


import com.example.deloitte_android_test.domain.model.Item
import com.example.deloitte_android_test.domain.repository.ItemRepository
import javax.inject.Inject

class CacheItemUseCase @Inject constructor(
    private val repo: ItemRepository
) {
    suspend operator fun invoke(item: Item) {
        return repo.insertItem(item)
    }
}