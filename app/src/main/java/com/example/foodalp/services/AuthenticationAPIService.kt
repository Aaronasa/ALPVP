package com.example.foodalp.services


import EmailRequest
import LoginRequest
import LoginResponse
import RegisterRequest
import RegisterResponse
import UpdateUserRequest
import UserResponse
import android.util.Log
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PUT
import retrofit2.http.Query

interface AuthenticationAPIService {

    // Create User (Register)
        @POST("public/create")
        suspend fun registerUser(@Body request: RegisterRequest): Response<RegisterResponse>

    // Login User
    @POST("public/login")
    suspend fun loginUser(@Body request: LoginRequest): Response<LoginResponse>

    // Update User
    @PUT("update")
    suspend fun updateUser(@Body request: UpdateUserRequest): Response<UserResponse>

    // Delete User
    @DELETE("delete")
    suspend fun deleteUser(@Query("id") id: Int): Response<UserResponse>

    @POST("/auth/read")
    suspend fun getUserData(
        @Header("x-API-Token") token: String,
        @Body emailRequest: EmailRequest
    ): Response<UserResponse>

    // Function to handle the API response
    suspend fun fetchUserData(apiService: AuthenticationAPIService, token: String, email: String): UserResponse? {
        return try {
            val response = apiService.getUserData(token, EmailRequest(email))
            if (response.isSuccessful) {
                response.body() // Return the UserResponse if successful
            } else {
                // Log or handle different response codes (e.g., 401, 403, 500)
                Log.e("API_ERROR", "Error: ${response.code()} - ${response.errorBody()?.string()}")
                null // Return null in case of failure
            }
        } catch (e: Exception) {
            Log.e("API_ERROR", "Exception: ${e.message}")
            null // Return null in case of exception
        }
    }

}
