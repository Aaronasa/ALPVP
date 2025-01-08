package com.example.foodalp.services

import android.util.Log
import com.example.foodalp.AppContainer
import com.example.foodalp.models.RestaurantModel
import com.example.foodalp.models.RestaurantResponse
import com.example.foodalp.models.ReviewModel
import com.example.foodalp.models.ReviewResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

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

    @POST("auth/reviews/Create")
    @Multipart
    suspend fun creatReviews(
        @Header("x-API-Token") token: String,
        @Part("userId") userId: RequestBody,
        @Part("restaurantId") restaurantId: RequestBody,
        @Part("content") content: RequestBody,
        @Part("rating") rating: RequestBody,
    ): ReviewModel

}