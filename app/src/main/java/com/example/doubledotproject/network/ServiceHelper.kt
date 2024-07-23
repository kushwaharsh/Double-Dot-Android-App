package com.example.doubledotproject.network

import com.example.doubledotproject.utiles.KeyConstants
import okhttp3.OkHttp
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class ServiceHelper {
    fun get() : Retrofit{
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)

        val httpClient = OkHttpClient.Builder()
        httpClient.connectTimeout(60 , TimeUnit.SECONDS)
            .readTimeout(60 , TimeUnit.SECONDS)
        httpClient.addInterceptor(logging)

        return Retrofit.Builder()
            .baseUrl(KeyConstants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient.build())
            .build()
    }

    fun getApi() = get().create(ApiService::class.java)
}