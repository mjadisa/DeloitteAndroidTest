package com.example.deloitte_android_test.di.module


import com.example.deloitte_android_test.presentation.mapper.ViewStateMapper
import com.example.deloitte_android_test.presentation.mapper.ViewStateMapperImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
abstract class ViewStateMapperModule {
    @Binds
    abstract fun providesViewStateMapper(mapperImp: ViewStateMapperImpl): ViewStateMapper
}

