package com.example.doubledotproject.apiResponse
import com.google.gson.annotations.SerializedName

data class AuthResponseModel(
    @SerializedName("code")
    val code: Int,
    @SerializedName("data")
    val `data`: LoginData,
    @SerializedName("isExist")
    val isExist: Boolean,
    @SerializedName("message")
    val message: String,
    @SerializedName("otp")
    val otp: String
)

data class Loc(
    @SerializedName("address")
    val address: String,
    @SerializedName("coordinates")
    val coordinates: List<Double>,
    @SerializedName("type")
    val type: String
)

data class LoginData(
    @SerializedName("city")
    val city: String,
    @SerializedName("counterCode")
    val counterCode: String,
    @SerializedName("counterIcon")
    val counterIcon: Any,
    @SerializedName("createdAt")
    val createdAt: String,
    @SerializedName("customer_id")
    val customerId: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("fullName")
    val fullName: String,
    @SerializedName("_id")
    val id: String,
    @SerializedName("isActive")
    val isActive: Boolean,
    @SerializedName("isDeleted")
    val isDeleted: Boolean,
    @SerializedName("jwtToken")
    val jwtToken: String,
    @SerializedName("location")
    val location: String,
    @SerializedName("phoneNumber")
    val phoneNumber: Long,
    @SerializedName("state")
    val state: String,
    @SerializedName("updatedAt")
    val updatedAt: String,
    @SerializedName("__v")
    val v: Int,
    @SerializedName("walletBalance")
    val walletBalance: String,
    @SerializedName("image")
    val image: String,
    @SerializedName("gender")
    val gender: String,
    @SerializedName("dob")
    val dob: String,
    @SerializedName("emailIsVerify")
    val emailIsVerify: Boolean
)
