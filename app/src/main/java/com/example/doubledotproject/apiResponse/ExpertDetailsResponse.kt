package com.example.doubledotproject.apiResponse

data class ExpertDetailsResponse(
    val code: Int,
    val `data`: List<ExpertDetailsData>,
    val message: String,
    val ratingDetails: List<ExpertRatingDetail>
)

data class ExpertDetailsData(
    val __v: Int,
    val _id: String,
    val aadharNo: String,
    val age: String,
    val availability: List<String>,
    val consultationFee: Int,
    val countryCode: String,
    val createdAt: String,
    val description: String,
    val deviceType: String,
    val discountedConsultationFee: Int,
    val email: String,
    val expert_id: String,
    val fcmToken: String,
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
    val updatedAt: String
)

data class ExpertRatingDetail(
    val _id: String,
    val createdAt: String,
    val description: String,
    val expertId: String,
    val fullName: String,
    val image: String,
    val rating: Double,
    val roomId: String,
    val sentBy: String,
    val userId: String
)