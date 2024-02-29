package com.example.deloitte_android_test.di.module

import com.example.deloitte_android_test.data.database.mapper.DbMapper
import com.example.deloitte_android_test.data.database.mapper.DbMapperImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DbMapperModule {
    @Binds
    abstract fun bindDbMapper(mapperImp: DbMapperImpl): DbMapper
}