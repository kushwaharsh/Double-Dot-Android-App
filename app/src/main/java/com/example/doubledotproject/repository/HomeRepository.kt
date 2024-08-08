package com.example.doubledotproject.repository

import com.example.doubledotproject.base.BaseRepository
import com.example.doubledotproject.network.ServiceHelper
import java.util.HashMap
import java.util.SimpleTimeZone

object HomeRepository : BaseRepository() {

    suspend fun getExpertList(token: String) = safeApiCall {
        ServiceHelper().getApi().getExpertList(token)
    }
    suspend fun getExpertData(token : String , expertId : String) = safeApiCall {
        ServiceHelper().getApi().getExpertDetails(token , expertId)
    }
    suspend fun getWalletTransactionDetails(token: String) = safeApiCall {
        ServiceHelper().getApi().getWalletTransactionDetails(token)
    }

    suspend fun addAmountToWallet(token: String , body : HashMap<String , Int>) = safeApiCall {
        ServiceHelper().getApi().addAmountToWallet(token , body )
    }

    suspend fun getWalletAmount(token: String) = safeApiCall {
        ServiceHelper().getApi().getWalletAmount(token)
    }

    suspend fun editUserProfile(token: String , param: HashMap<String, Any>) = safeApiCall {
        ServiceHelper().getApi().editUserProfile(token , param)
    }

    suspend fun staticContentDetails(type : String) = safeApiCall {
        ServiceHelper().getApi().staticContentDetails(type)
    }

    suspend fun deleteUserAccount (token: String) = safeApiCall {
        ServiceHelper().getApi().deleteUserAccount(token)
    }

    suspend fun logoutUser(token: String) = safeApiCall {
        ServiceHelper().getApi().logoutUser(token)
    }
}