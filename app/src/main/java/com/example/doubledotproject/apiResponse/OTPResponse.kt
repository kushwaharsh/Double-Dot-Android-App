package com.example.doubledotproject.apiResponse

import com.google.gson.annotations.SerializedName

data class OTPResponse(
    @SerializedName("code") val code: Int?,
    @SerializedName("message") val message: String?,
    @SerializedName("data") val data: OTPData?
)

data class OTPData(
    @SerializedName("otp") val otp: String?,
    @SerializedName("phoneNumber") val phoneNumber: Long?
)

