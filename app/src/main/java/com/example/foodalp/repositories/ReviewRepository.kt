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
        return when (val data = response.data) {
            is List<*> -> data.filterIsInstance<ReviewModel>() // Ensure all items are `ReviewModel`
            is ReviewModel -> listOf(data) // Wrap a single `ReviewModel` in a list
            else -> emptyList() // Return empty list if data is null or invalid
        }
    }

    suspend fun updateReview(id: Int, request: UpdateReviewRequest): ReviewModel {
        return apiService.updateReview(id, request)
    }

    suspend fun deleteReview(request: DeleteReviewRequest): ReviewModel {
        return apiService.deleteReview(request)
    }
}
