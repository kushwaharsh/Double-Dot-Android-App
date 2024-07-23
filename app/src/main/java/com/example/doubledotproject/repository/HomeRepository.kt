package com.example.doubledotproject.repository

import com.example.doubledotproject.base.BaseRepository
import com.example.doubledotproject.network.ServiceHelper
import java.util.HashMap

object HomeRepository : BaseRepository() {

    suspend fun getExpertList(token: String) = safeApiCall {
        ServiceHelper().getApi().getExpertList(token)
    }
}