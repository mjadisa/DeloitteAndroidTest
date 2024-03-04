package com.example.deloitte_android_test.utils

import com.example.deloitte_android_test.data.enums.ErrorModel
import com.example.deloitte_android_test.data.enums.ErrorType
import com.example.deloitte_android_test.data.network.model.ErrorMessage
import com.example.deloitte_android_test.data.network.model.ErrorResponse
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import retrofit2.Response
import java.lang.reflect.Type

fun Response<*>.mapErrorResponse(): ErrorResponse {
    val error: Type = object : TypeToken<ErrorResponse>() {}.type
    return try {
        Gson().fromJson(errorBody()?.string(), error)
    } catch (e: Exception) {
        ErrorResponse(ErrorMessage(EMPTY_STRING))
    }
}

fun ErrorResponse.mapErrorModel(): ErrorModel {
    return ErrorModel(ErrorType.UNKNOWN, this.error.message)
}

fun Double.roundTo(n: Int): Double {
    return "%.${n}f".format(this).toDouble()
}