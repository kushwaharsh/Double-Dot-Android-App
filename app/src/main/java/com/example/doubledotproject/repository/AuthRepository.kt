package com.example.doubledotproject.repository

import com.example.doubledotproject.base.BaseRepository
import com.example.doubledotproject.network.ServiceHelper

object AuthRepository : BaseRepository() {

    suspend fun loginInWithMob(params: HashMap<String , String>) = safeApiCall {
        ServiceHelper().getApi().loginInWithMob(params)
    }
    suspend fun resendMobOtp(phoneNumber:Long) = safeApiCall {
        ServiceHelper().getApi().resendMobOtp(phoneNumber)
    }
    suspend fun signUpUser(params: HashMap<String, String>) = safeApiCall {
        ServiceHelper().getApi().signUpUser(params)
    }
}