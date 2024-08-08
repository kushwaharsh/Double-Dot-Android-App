package com.example.doubledotproject.apiResponse

data class EditUserProfileResponseModel(
    val code: Int,
    val `data`: LoginData,
    val message: String
)

data class ProfileEditedData(
    val __v: Int,
    val _id: String,
    val city: String,
    val counterCode: String,
    val createdAt: String,
    val customer_id: String,
    val deviceType: String,
    val dob: String,
    val email: String,
    val emailIsVerify: Boolean,
    val fcmToken: String,
    val fullName: String,
    val gender: String,
    val image: String,
    val isActive: Boolean,
    val isDeleted: Boolean,
    val jwtToken: String,
    val location: String,
    val phoneNumber: Any,
    val state: String,
    val updatedAt: String,
    val walletBalance: String
)