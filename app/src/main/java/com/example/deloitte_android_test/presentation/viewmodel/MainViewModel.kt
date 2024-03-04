package com.example.deloitte_android_test.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.deloitte_android_test.domain.usecases.CacheItemUseCase
import com.example.deloitte_android_test.domain.usecases.GetRemoteItemListUseCase
import com.example.deloitte_android_test.presentation.mapper.ViewStateMapperImpl
import com.example.deloitte_android_test.presentation.viewstate.ItemListViewState
import com.example.deloitte_android_test.presentation.viewstate.ItemViewState
import com.example.deloitte_android_test.utils.DataHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getRemoteItemListUseCase: GetRemoteItemListUseCase,
    private val cacheItemUseCase: CacheItemUseCase,
    private val mapperImpl: ViewStateMapperImpl
) : ViewModel() {
    private val _result = MutableStateFlow<DataHandler<ItemListViewState>>(
        DataHandler.SUCCESS(
            ItemListViewState()
        )
    )
    val result: Flow<DataHandler<ItemListViewState>> = _result

    fun getData() {
        viewModelScope.launch {
            _result.value = DataHandler.LOADING()
            getRemoteItemListUseCase().data?.let {
                _result.value =
                    DataHandler.SUCCESS(mapperImpl.mapDomainItemListResultToViewState(it))
            }
        }
    }

    fun addItemToWishList(itemViewState: ItemViewState) {
        itemViewState.wishListStatus = true
        cacheSelectedItem(
            itemViewState
        )
    }

    fun addItemToCart(itemViewState: ItemViewState) {
        itemViewState.cartStatus = true
        itemViewState.quantity = ++itemViewState.quantity
        cacheSelectedItem(
            itemViewState
        )
    }

    private fun cacheSelectedItem(itemViewState: ItemViewState) {
        viewModelScope.launch {
            cacheItemUseCase(mapperImpl.mapItemViewStateToDomain(itemViewState))
        }
    }


}