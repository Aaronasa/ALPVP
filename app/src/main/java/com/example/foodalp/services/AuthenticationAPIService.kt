package com.example.foodalp.services


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

}