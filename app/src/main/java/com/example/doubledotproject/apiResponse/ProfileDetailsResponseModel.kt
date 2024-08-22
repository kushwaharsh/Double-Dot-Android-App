package com.example.doubledotproject.apiResponse

data class ProfileDetailsResponseModel(
    val code: Int,
    val `data`: List<LoginData>,
    val message: String
)

data class ProfileData(
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
    val isActive: Boolean,
    val isDeleted: Boolean,
    val jwtToken: String,
    val location: String,
    val phoneNumber: Long,
    val state: String,
    val updatedAt: String,
    val walletBalance: String
)