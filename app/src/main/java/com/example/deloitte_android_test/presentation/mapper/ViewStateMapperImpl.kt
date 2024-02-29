package com.example.deloitte_android_test.presentation.mapper


import com.example.deloitte_android_test.domain.model.Item
import com.example.deloitte_android_test.domain.model.ItemListResult
import com.example.deloitte_android_test.presentation.viewstate.ItemListViewState
import com.example.deloitte_android_test.presentation.viewstate.ItemViewState
import javax.inject.Inject

class ViewStateMapperImpl @Inject constructor() : ViewStateMapper {
    override fun mapDomainItemListResultToViewState(itemListResult: ItemListResult): ItemListViewState {
        return with(itemListResult) {
            ItemListViewState(
                itemsViewState = items.map {
                    mapDomainItemToViewState(it)
                } as ArrayList<ItemViewState>

            )
        }
    }

    override fun mapItemViewStateToDomain(itemViewState: ItemViewState): Item {
        return with(itemViewState) {
            Item(name, image, price, stock, category, oldPrice, productId)
        }
    }

    fun mapDomainItemToViewState(item: Item): ItemViewState {
        return with(item) {
            ItemViewState(
                name = name,
                category = category,
                image = image,
                price = price,
                stock = stock,
                oldPrice = oldPrice,
                productId = productId

            )
        }
    }
}



