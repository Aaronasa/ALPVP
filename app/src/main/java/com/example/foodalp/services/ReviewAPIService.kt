package com.example.foodalp.services

import android.util.Log
import com.example.foodalp.AppContainer
import com.example.foodalp.models.ApiRespoonse
import com.example.foodalp.models.CreateReviewRequest
import com.example.foodalp.models.RestaurantModel
import com.example.foodalp.models.RestaurantResponse
import com.example.foodalp.models.ReviewModel
import com.example.foodalp.models.ReviewResponse
import com.example.foodalp.models.UpdateReviewRequest
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
//import okhttp3.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path

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

    @GET("admin/reviews/readall")
    suspend fun getAllReviewsAdmin(
        @Header("x-API-Token") token: String
    ): ReviewResponse {
        Log.d("RestaurantService", "Fetching all restaurants...")
        val response = AppContainer.reviewService.getAllReviewsAdmin(token)
        Log.d("RestaurantService", "Successfully fetched restaurants: ${response.data}")
        return response
    }

    @GET("auth/reviews/read/{id}")
    suspend fun getReviewById(
        @Header("x-API-Token") token: String,
        @Path("id") id: Int
    ): Response<ApiRespoonse<ReviewModel>>

    @GET("admin/reviews/read/{id}")
    suspend fun getReviewByIdAdmin(
        @Header("x-API-Token") token: String,
        @Path("id") id: Int
    ): Response<ApiRespoonse<ReviewModel>>

    @POST("auth/reviews/Create")
    suspend fun creatReviews(
        @Header("x-API-Token") token: String,
        @Body ReviewRequest: CreateReviewRequest
    ): ReviewModel

    @POST("admin/reviews/Create")
    suspend fun creatReviewsAdmin(
        @Header("x-API-Token") token: String,
        @Body ReviewRequest: CreateReviewRequest
    ): ReviewModel

    @PUT("auth/reviews/update/{id}")
    suspend fun updateReview(
        @Header("x-API-Token") token: String,
        @Path("id") id: Int,
        @Body ReviewRequest: UpdateReviewRequest
    ): ReviewModel

    @PUT("admin/reviews/update/{id}")
    suspend fun updateReviewAdmin(
        @Header("x-API-Token") token: String,
        @Path("id") id: Int,
        @Body ReviewRequest: UpdateReviewRequest
    ): ReviewModel


    @DELETE("auth/reviews/delete/{id}")
    suspend fun deleteReview(
        @Header("x-API-Token") token: String,
        @Path("id") id: Int
    ): ReviewModel

    @DELETE("Admin/reviews/delete/{id}")
    suspend fun deleteReviewAdmin(
        @Header("x-API-Token") token: String,
        @Path("id") id: Int
    ): ReviewModel




    @GET("auth/reviews/restaurant/{restaurantId}")
    suspend fun getReviewsByRestaurant(
        @Header("x-API-Token") token: String,  // Token header
        @Path("restaurantId") restaurantId: Int // Path parameter for restaurant ID
    ): ReviewResponse



}