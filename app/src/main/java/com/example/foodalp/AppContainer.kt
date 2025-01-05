package com.example.foodalp

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.example.foodalp.services.AuthenticationAPIService
import com.example.foodalp.services.RestaurantAPIService
import com.example.foodalp.services.UserAPIService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object AppContainer {

    private const val BASE_URL = "http://192.168.1.5:3000/" // Ganti dengan URL API Anda yang sebenarnya

    lateinit var sharedPreferences: SharedPreferences

    // Inisialisasi SharedPreferences dan DataStore
    fun initialize(context: Context) {
        sharedPreferences = context.getSharedPreferences("user_preferences", Context.MODE_PRIVATE)
    }

    // DataStore untuk preferensi
    val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_data")

    // Menyiapkan HTTP Logging Interceptor
    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    // Membuat OkHttpClient dengan logging dan token handling
    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .addInterceptor { chain ->
            val originalRequest = chain.request()

            // Retrieve token from SharedPreferences
            val token = sharedPreferences.getString("USER_TOKEN", null)

            // Log the token to ensure it's being retrieved
            Log.d("AppContainer", "Token ditemukan: $token")

            val requestBuilder = originalRequest.newBuilder()

            // If the token is present, add it twice to the URL parameters
            token?.let {
                // Add token twice to query parameters
                val newUrl = originalRequest.url
                    .newBuilder()
                    .addQueryParameter("token", it) // First token in params
                    .build()

                // Apply the new URL to the request
                requestBuilder.url(newUrl)

                // Optionally, still send the token in the header as x-API-Token
                requestBuilder.addHeader("x-API-Token", it)
            }

            val newRequest = requestBuilder.build()
            chain.proceed(newRequest)
        }
        .build()

    // Retrofit Instance
    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient) // Gunakan OkHttpClient dengan interceptor
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
    val restaurantService: RestaurantAPIService by lazy {
        retrofit.create(RestaurantAPIService::class.java)
    }

    val reviewService: ReviewAPIService by lazy {
        retrofit.create(ReviewAPIService::class.java)
    }
}
