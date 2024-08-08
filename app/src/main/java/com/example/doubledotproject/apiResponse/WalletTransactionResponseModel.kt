package com.example.doubledotproject.apiResponse

data class WalletTransactionResponseModel(
    val code: Int,
    val `data`: WalletData?,
    val message: String
)

data class WalletData(
    val currentAmount: CurrentAmount,
    val transHistory: List<TransHistory>
)

data class TransHistory(
    val __v: Int,
    val _id: String,
    val amount: Double,
    val amountType: String,
    val createdAt: String,
    val durationMinutes: Int,
    val type: String,
    val updatedAt: String,
    val userId: String
)

data class CurrentAmount(
    val __v: Int,
    val _id: String,
    val amount: Double,
    val createdAt: String,
    val updatedAt: String,
    val userId: String
)

