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
    suspend fun registerUser(@Body request: RegisterRequest
    ): Response<RegisterResponse>

    @POST("public/login")
    suspend fun loginUser(@Body request: LoginRequest
    ): Response<LoginResponse>

    @POST("/auth/read")
    suspend fun getUserData(
        @Header("x-API-Token") token: String,
        @Body emailRequest: EmailRequest
    ): Response<UserResponse>

    @POST("/auth/logout")
    suspend fun logout(
        @Header("x-API-Token") token: String
    ): Response<LogoutResponse>

    @PUT("/auth/update")
    suspend fun updateUser(
        @Header("x-API-Token") token: String,
        @Body request: UpdateUserRequest
    ): Response<UserResponse>

    @DELETE("/auth/delete")
    suspend fun deleteUser(
        @Header("x-API-Token") token: String
    ): Response<DeleteResponse>

    @GET("/admin/read")
    suspend fun getAllUsers(
        @Header("x-API-Token") token: String
    ): Response<List<UserModel>>

}
