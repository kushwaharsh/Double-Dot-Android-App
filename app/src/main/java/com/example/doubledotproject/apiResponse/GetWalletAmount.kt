package com.example.doubledotproject.apiResponse

data class GetWalletAmount(
    val code: Int,
    val `data`: WalletAmount,
    val message: String
)
data class WalletAmount(
    val currentAmount: CurrentWalletAmount
)

data class CurrentWalletAmount(
    val __v: Int,
    val _id: String,
    val amount: Int,
    val createdAt: String,
    val updatedAt: String,
    val userId: String
)