package com.example.foodalp.repositories

import com.example.foodalp.models.*
import com.example.foodalp.services.ReviewAPIService

class ReviewRepository(private val apiService: ReviewAPIService) {

    suspend fun createReview(request: CreateReviewRequest): ReviewModel {
        return apiService.createReview(request)
    }

    suspend fun getAllReviews(): ReviewResponse {
        return apiService.getAllReviews()
    }

    suspend fun getReviewsByRestaurant(restaurantId: Int): List<ReviewModel> {
        val response = apiService.getReviewsByRestaurant(restaurantId)
        val data = response.data

        // Safely cast `data` to a list of ReviewModel
        val listData = data as? List<ReviewModel>
        if (listData != null) {
            return listData
        }

        // Safely cast `data` to a single ReviewModel
        val singleData = data as? ReviewModel
        if (singleData != null) {
            return listOf(singleData)
        }

        // Return an empty list if neither case matches
        return emptyList()
    }

    suspend fun updateReview(id: Int, request: UpdateReviewRequest): ReviewModel {
        return apiService.updateReview(id, request)
    }

    suspend fun deleteReview(request: DeleteReviewRequest): ReviewModel {
        return apiService.deleteReview(request)
    }
}
