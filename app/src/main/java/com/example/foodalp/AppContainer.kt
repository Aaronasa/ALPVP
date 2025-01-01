package com.example.foodalp

import com.example.foodalp.services.AuthenticationAPIService
import com.example.foodalp.services.UserAPIService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object AppContainer {

    private const val BASE_URL = "http://192.168.18.244:3000/" // Replace with your actual API base URL

    // Set up HTTP Logging Interceptor
    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    // Create an OkHttpClient with the logging interceptor
    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .build()

    // Retrofit Instance
    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient) // Use the OkHttp client with interceptor
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    // API Services
    val authService: AuthenticationAPIService by lazy {
        retrofit.create(AuthenticationAPIService::class.java)
    }

    val userService: UserAPIService by lazy {
        retrofit.create(UserAPIService::class.java)
    }
}
