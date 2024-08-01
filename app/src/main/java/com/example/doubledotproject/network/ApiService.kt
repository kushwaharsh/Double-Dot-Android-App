package com.example.doubledotproject.network

import com.example.doubledotproject.apiResponse.AuthResponseModel
import com.example.doubledotproject.apiResponse.ExpertDetailsResponse
import com.example.doubledotproject.apiResponse.GetExpertListResponse
import com.example.doubledotproject.apiResponse.LoginData
import com.example.doubledotproject.apiResponse.OTPResponse
import com.example.doubledotproject.utiles.KeyConstants
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {
    @POST(KeyConstants.LOGIN_WITH_MOB)
    suspend fun loginInWithMob(@Body data : HashMap<String , String>) : AuthResponseModel?

    @FormUrlEncoded
    @POST(KeyConstants.RESEND_MOB_OTP)
    suspend fun resendMobOtp(@Field ("phoneNumber") phoneNumber:Long) : OTPResponse?

    @POST(KeyConstants.SIGN_UP_USER)
    suspend fun signUpUser(@Body data : HashMap<String , String>) : AuthResponseModel?

    @GET(KeyConstants.GET_EXPERT_LIST)
    suspend fun getExpertList( @Header("Authorization") token:String) : GetExpertListResponse?

    @GET(KeyConstants.GET_EXPERT_DETAILS)
    suspend fun getExpertDetails(
        @Header("Authorization") token:String ,
        @Query("expertId") expertId:String) : ExpertDetailsResponse?
}