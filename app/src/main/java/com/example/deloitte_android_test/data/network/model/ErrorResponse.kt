package com.example.deloitte_android_test.data.network.model

import com.google.gson.annotations.SerializedName

data class ErrorResponse(
    @SerializedName("error") val error: ErrorMessage
)
