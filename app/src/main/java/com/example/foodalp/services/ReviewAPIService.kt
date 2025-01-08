package com.example.foodalp.services

import android.util.Log
import com.example.foodalp.AppContainer
import com.example.foodalp.models.RestaurantResponse
import com.example.foodalp.models.ReviewResponse
import retrofit2.http.GET
import retrofit2.http.Header

interface ReviewAPIService {

    @GET("auth/reviews/readall")
    suspend fun getAllReviews(
        @Header("x-API-Token") token: String
    ): ReviewResponse {
        Log.d("RestaurantService", "Fetching all restaurants...")
        val response = AppContainer.reviewService.getAllReviews(token)
        Log.d("RestaurantService", "Successfully fetched restaurants: ${response.data}")
        return response
    }

}