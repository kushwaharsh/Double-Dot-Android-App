package com.example.doubledotproject.apiResponse

import com.google.gson.annotations.SerializedName

data class StaticContentResponseModel(
    @SerializedName("code")
    val code: Int,
    @SerializedName("data")
    val `data`: StaticData,
    @SerializedName("message")
    val message: String
)

data class StaticData(
    @SerializedName("createdAt")
    val createdAt: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("_id")
    val id: String,
    @SerializedName("isActive")
    val isActive: Boolean,
    @SerializedName("phoneNumber")
    val phoneNumber: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("type")
    val type: String,
    @SerializedName("updatedAt")
    val updatedAt: String,
    @SerializedName("__v")
    val v: Int,
    @SerializedName("description")
    val description: String,
)