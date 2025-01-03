package com.example.foodalp

import android.content.Context
import android.content.SharedPreferences
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.example.foodalp.services.AuthenticationAPIService
import com.example.foodalp.services.UserAPIService
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object AppContainer {

    private const val BASE_URL = "http://192.168.1.5:3000/" // Replace with your actual API base URL

    lateinit var sharedPreferences: SharedPreferences

    // Initialize SharedPreferences and DataStore
    fun initialize(context: Context) {
        sharedPreferences = context.getSharedPreferences("user_preferences", Context.MODE_PRIVATE)
    }

    // DataStore property for preferences
    val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_data")

    // Set up HTTP Logging Interceptor
    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    // Create an OkHttpClient with the logging interceptor and token handling
    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .addInterceptor { chain ->
            var request: Request = chain.request()

            // Add Authorization header if token exists
            val token = sharedPreferences.getString("USER_TOKEN", null)
            token?.let {
                request = request.newBuilder()
                    .addHeader("Authorization", "Bearer $it")
                    .build()
            }

            chain.proceed(request)
        }
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
