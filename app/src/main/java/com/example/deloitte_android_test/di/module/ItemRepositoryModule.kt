package com.example.deloitte_android_test.di.module

import com.example.deloitte_android_test.data.repository.ItemRepositoryImpl
import com.example.deloitte_android_test.domain.repository.ItemRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class ItemRepositoryModule {
    @Binds
    abstract fun bindItemRepository(repositoryImpl: ItemRepositoryImpl): ItemRepository
}