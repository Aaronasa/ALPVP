package com.example.foodalp

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.example.foodalp.services.AuthenticationAPIService
import com.example.foodalp.services.CityAPIService
import com.example.foodalp.services.RestaurantAPIService
import com.example.foodalp.services.ReviewAPIService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object AppContainer {

    private const val BASE_URL = "http://192.168.18.244:3000/" // Ganti dengan URL API Anda yang sebenarnya

     lateinit var sharedPreferences: SharedPreferences

    // Initialising SharedPreferences dan DataStore
    fun initialize(context: Context) {
        sharedPreferences = context.getSharedPreferences("user_preferences", Context.MODE_PRIVATE)
    }

    // DataStore untuk preferensi
    val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_data")

    // HTTP Logging Interceptor
    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    // Fungsi untuk mengambil token dari SharedPreferences
    fun getToken(): String? {
        val token = sharedPreferences.getString("user_session", null)
        return token
    }

    // Membuat OkHttpClient dengan logging dan token handling
    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .addInterceptor { chain ->
            val originalRequest = chain.request()
            val token = getToken()// Ambil token terbaru dari SharedPreferences
            val requestBuilder = originalRequest.newBuilder()

            // Tambahkan token ke query parameter dan header jika ada
            token?.let {
                val newUrl = originalRequest.url
                    .newBuilder()
                    .build()
                requestBuilder.url(newUrl)
                requestBuilder.addHeader("x-API-Token", it) // Tambahkan token ke header
            }

            val newRequest = requestBuilder.build()
            chain.proceed(newRequest)
        }
        .build()

    // Retrofit Instance
    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    // API Services
    val authService: AuthenticationAPIService by lazy {
        retrofit.create(AuthenticationAPIService::class.java)
    }

    val restaurantService: RestaurantAPIService by lazy {
        retrofit.create(RestaurantAPIService::class.java)
    }
    val reviewService: ReviewAPIService by lazy {
        retrofit.create(ReviewAPIService::class.java)
    }

    val cityService: CityAPIService by lazy {
        retrofit.create(CityAPIService::class.java)
    }
}
