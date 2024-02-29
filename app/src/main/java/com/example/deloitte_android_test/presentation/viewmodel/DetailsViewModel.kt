package com.example.deloitte_android_test.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.deloitte_android_test.domain.usecases.GetItemUseCase
import com.example.deloitte_android_test.presentation.mapper.ViewStateMapperImpl
import com.example.deloitte_android_test.presentation.viewstate.ItemViewState
import com.example.deloitte_android_test.utils.DataHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val getItemUseCase: GetItemUseCase,
    private val mapperImpl: ViewStateMapperImpl
) : ViewModel() {
    private val _result = MutableStateFlow<DataHandler<ItemViewState>>(
        DataHandler.SUCCESS(
            ItemViewState()
        )
    )
    val result: Flow<DataHandler<ItemViewState>> = _result

    fun getItemData(id: String) {
        viewModelScope.launch {
            _result.value = DataHandler.LOADING()
            getItemUseCase(id).data?.let {
                _result.value = DataHandler.SUCCESS(mapperImpl.mapDomainItemToViewState(it))
            }
        }
    }


}