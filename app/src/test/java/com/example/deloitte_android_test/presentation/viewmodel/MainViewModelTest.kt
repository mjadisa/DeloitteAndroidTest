package com.example.deloitte_android_test.presentation.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.deloitte_android_test.domain.model.ItemListResult
import com.example.deloitte_android_test.domain.usecases.CacheItemUseCase
import com.example.deloitte_android_test.domain.usecases.GetRemoteItemListUseCase
import com.example.deloitte_android_test.presentation.mapper.ViewStateMapperImpl
import com.example.deloitte_android_test.presentation.viewstate.ItemListViewState
import com.example.deloitte_android_test.utils.DataHandler

import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class MainViewModelTest {
    private val getRemoteItemListUseCase = mockk<GetRemoteItemListUseCase>()
    private val cacheItemUseCase = mockk<CacheItemUseCase>()
    private val mapperImpl =  mockk<ViewStateMapperImpl>()
    private val response = mockk<ItemListResult>()
    private val itemListViewState = mockk<ItemListViewState>()
    private lateinit var viewModel: MainViewModel

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    @Before
    fun setUp() {
        viewModel = MainViewModel(getRemoteItemListUseCase,
            cacheItemUseCase,
            mapperImpl)
    }

    @Test
    fun `Given that the device is online WHEN item list is requested THEN item list is returned`() = runTest {

        // WHEN
        coEvery { getRemoteItemListUseCase() } returns DataHandler.SUCCESS(response)
        coEvery {  mapperImpl.mapDomainItemListResultToViewState(response) } returns itemListViewState
        viewModel.getData()

        // THEN
        Assert.assertEquals(viewModel.result.first().data, mapperImpl.mapDomainItemListResultToViewState(response))
    }

}