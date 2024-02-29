package com.example.deloitte_android_test.di.module

import com.example.deloitte_android_test.data.repository.ItemListRepositoryImpl
import com.example.deloitte_android_test.domain.repository.ItemListRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class ItemListRepositoryModule {
    @Binds
    abstract fun bindItemListRepository(repositoryImpl: ItemListRepositoryImpl): ItemListRepository
}