package com.example.deloitte_android_test.utils

import com.example.deloitte_android_test.data.enums.ErrorModel


sealed class DataHandler<T>(
    val data: T? = null,
    val error: ErrorModel? = null,
) {
    class SUCCESS<T>(data: T) : DataHandler<T>(data)
    class ERROR<T>(data: T? = null, error: ErrorModel) : DataHandler<T>(data, error)
    class LOADING<T> : DataHandler<T>()
}
