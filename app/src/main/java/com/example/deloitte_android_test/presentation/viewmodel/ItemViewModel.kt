package com.example.deloitte_android_test.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.deloitte_android_test.domain.usecases.CacheItemUseCase
import com.example.deloitte_android_test.domain.usecases.GetLocalItemListUseCase
import com.example.deloitte_android_test.domain.usecases.RemoveItemUseCase
import com.example.deloitte_android_test.presentation.mapper.ViewStateMapperImpl
import com.example.deloitte_android_test.presentation.viewstate.ItemViewState
import com.example.deloitte_android_test.utils.DataHandler
import com.example.deloitte_android_test.utils.roundTo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ItemViewModel @Inject constructor(
    private val getLocalItemListUseCase: GetLocalItemListUseCase,
    private val mapperImpl: ViewStateMapperImpl,
    private val cacheItemUseCase: CacheItemUseCase,
    private val removeItemUseCase: RemoveItemUseCase
) : ViewModel() {
    private val _items = MutableStateFlow<DataHandler<List<ItemViewState>>>(
        DataHandler.SUCCESS(
            listOf()
        )
    )
    val items: Flow<DataHandler<List<ItemViewState>>> = _items

    private val _basketItems = MutableStateFlow<DataHandler<Pair<List<ItemViewState>, Double>>>(
        DataHandler.SUCCESS(
            Pair(listOf(), INITIAL_COST_VALUE)
        )
    )

    val basketItems: Flow<DataHandler<Pair<List<ItemViewState>, Double>>> = _basketItems

    fun getWishListItems() {
        viewModelScope.launch {
            _items.value = DataHandler.LOADING()
            getLocalItemListUseCase().data?.let {
                _items.value =
                    DataHandler.SUCCESS(mapperImpl.mapDomainItemsToViewState(it.filter { item ->
                        item.wishListStatus
                    }))
            }

        }
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


    fun removeItemFromWishlist(itemViewState: ItemViewState) {
        itemViewState.wishListStatus = false
        removeSelectedItem(itemViewState = itemViewState)

    }


    private fun removeSelectedItem(itemViewState: ItemViewState) {
        viewModelScope.launch {
            removeItemUseCase(itemViewState.productId)
        }
    }

    fun getBasketItems() {
        viewModelScope.launch {
            _basketItems.value = DataHandler.LOADING()
            getLocalItemListUseCase().data?.let {
                val products = mapperImpl.mapDomainItemsToViewState(it.filter { item ->
                    item.cartStatus
                })

                _basketItems.value = DataHandler.SUCCESS(Pair(products, itemsTotalCost(products)))
            }

        }
    }

    fun removeItemFromCart(itemViewState: ItemViewState) {
        itemViewState.cartStatus = false
        removeSelectedItem(itemViewState = itemViewState)

    }

    private fun itemsTotalCost(items: List<ItemViewState>): Double {
        var totalCost = INITIAL_COST_VALUE
        items.forEach {
            totalCost += it.price.times(it.quantity)
        }
        return totalCost.roundTo(NUMBER_OF_DECIMAL_PLACES)
    }

    companion object {
        const val INITIAL_COST_VALUE = 0.0
        const val NUMBER_OF_DECIMAL_PLACES = 2
    }


}