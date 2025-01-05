package com.example.foodalp.services

import com.example.foodalp.models.*
import okhttp3.RequestBody
import retrofit2.http.*

interface ReviewAPIService {

    @POST("reviews/Create")
    suspend fun createReview(@Body review: CreateReviewRequest): ReviewModel

    @GET("reviews/readall")
    suspend fun getAllReviews(): ReviewResponse

    @GET("reviews/restaurant/{restaurantId}")
    suspend fun getReviewsByRestaurant(@Path("restaurantId") restaurantId: Int): ReviewResponse

    @PUT("reviews/update/{id}")
    suspend fun updateReview(@Path("id") id: Int, @Body request: UpdateReviewRequest): ReviewModel

    @DELETE("reviews/delete")
    suspend fun deleteReview(@Body request: DeleteReviewRequest): ReviewModel
}
