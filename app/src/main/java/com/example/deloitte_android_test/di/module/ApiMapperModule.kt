package com.example.deloitte_android_test.di.module

import com.example.deloitte_android_test.data.network.mapper.ApiMapper
import com.example.deloitte_android_test.data.network.mapper.ApiMapperImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
abstract class ApiMapperModule {
    @Binds
    abstract fun bindApiMapper(mapperImp: ApiMapperImpl): ApiMapper
}

