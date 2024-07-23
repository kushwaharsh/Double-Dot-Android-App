package com.example.doubledotproject.apiResponse

data class GetExpertListResponse(
    val code: Int,
    val `data`: List<Data>,
    val message: String
)

data class Data(
    val __v: Int,
    val _id: String,
    val aadharNo: String,
    val age: String,
    val availability: List<String>,
    val city: String,
    val consultationFee: Int,
    val countryCode: String,
    val createdAt: String,
    val description: String,
    val discountedConsultationFee: Int,
    val email: String,
    val expert_id: String,
    val fullName: String,
    val gender: String,
    val image: String,
    val isActive: Boolean,
    val isAvilable: String,
    val isDeleted: Boolean,
    val isOnline: String,
    val jwtToken: String,
    val keywordExpertise: List<String>,
    val language: String,
    val location: String,
    val panNo: String,
    val phoneNumber: Long,
    val platormFee: Int,
    val rating: Double,
    val sessionCount: Int,
    val state: String,
    val updatedAt: String
)