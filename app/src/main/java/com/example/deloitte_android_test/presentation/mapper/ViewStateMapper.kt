package com.example.deloitte_android_test.presentation.mapper


import com.example.deloitte_android_test.domain.model.Item
import com.example.deloitte_android_test.domain.model.ItemListResult
import com.example.deloitte_android_test.presentation.viewstate.ItemListViewState
import com.example.deloitte_android_test.presentation.viewstate.ItemViewState


interface ViewStateMapper {
    fun mapDomainItemListResultToViewState(itemListResult: ItemListResult): ItemListViewState
    fun mapItemViewStateToDomain(itemViewState: ItemViewState): Item

    fun mapDomainItemsToViewState(items: List<Item>): List<ItemViewState>
}