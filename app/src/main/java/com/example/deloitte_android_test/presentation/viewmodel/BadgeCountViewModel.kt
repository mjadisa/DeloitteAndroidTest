package com.example.deloitte_android_test.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.deloitte_android_test.domain.usecases.GetItemListUseCase
import com.example.deloitte_android_test.presentation.mapper.ViewStateMapperImpl
import com.example.deloitte_android_test.presentation.viewstate.ItemViewState
import com.example.deloitte_android_test.utils.DataHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class BadgeCountViewModel @Inject constructor(
    private val getItemListUseCase: GetItemListUseCase,
    private val mapperImpl: ViewStateMapperImpl
) : ViewModel() {
    private val _badgeCount = MutableStateFlow<DataHandler<Pair<Int, Int>>>(
        DataHandler.SUCCESS(
            Pair(0, 0)
        )
    )
    val badgeCount: Flow<DataHandler<Pair<Int, Int>>> = _badgeCount

    fun getItemCount() {
        viewModelScope.launch {
            getItemListUseCase().data?.collect {
                val items = mapperImpl.mapDomainItemsToViewState(it)
                _badgeCount.value =
                    DataHandler.SUCCESS(Pair(getWishListCount(items), getCartCount(items)))
            }
        }
    }

    private fun getWishListCount(items: List<ItemViewState>) =
        items.filter {
            it.wishListStatus
        }.size


    private fun getCartCount(items: List<ItemViewState>) = items.filter {
        it.cartStatus
    }.size


}