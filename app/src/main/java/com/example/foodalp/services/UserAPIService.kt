package com.example.foodalp.services

import com.example.foodalp.models.GeneralResponseModel
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Header
import retrofit2.http.POST

interface UserAPIService{
    // Logout User
    @POST("/api/user/logout")
    suspend fun logoutUser(@Header("x-API-Token") token: String): Response<UserResponse>
}