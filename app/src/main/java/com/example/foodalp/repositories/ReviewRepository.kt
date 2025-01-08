package com.example.foodalp.repositories

import android.util.Log
import com.example.foodalp.models.*
import com.example.foodalp.services.ReviewAPIService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException

class ReviewRepository(private val apiService: ReviewAPIService) {

    suspend fun getAllReviews(token : String): List<ReviewModel> =
        withContext(Dispatchers.IO){
            try {
                // Log request details (optional, useful for debugging)
                Log.d("Repository", "Fetching all restaurants...")
                // Make the API call
                val Review =  apiService.getAllReviews(token)
                // Log success response (optional)
                Log.d("Repository", "Successfully fetched restaurants: ${Review.data}")
                Review.data
            } catch (e: HttpException) {
                // Check for specific HTTP status codes
                if (e.code() == 403) {
                    Log.e("Repository", "403 Forbidden: ${e.response()?.errorBody()?.string()}")
                    throw Exception("Access denied: Please check your authentication or permissions.")
                } else {
                    Log.e("Repository", "HTTP error: ${e.code()} - ${e.message()}")
                    throw e
                }
            } catch (e: Exception) {
                // Handle general exceptions
                Log.e("Repository", "Error fetching restaurants: ${e.message}", e)
                throw e
            }
    }

//    suspend fun createReview(request: CreateReviewRequest): ReviewModel {
//        return apiService.createReview(request)
//    }
//
//
//    suspend fun getReviewsByRestaurant(restaurantId: Int): List<ReviewModel> {
//        val response = apiService.getReviewsByRestaurant(restaurantId)
//        val data = response.data
//
//        // Safely cast `data` to a list of ReviewModel
//        val listData = data as? List<ReviewModel>
//        if (listData != null) {
//            return listData
//        }
//
//        // Safely cast `data` to a single ReviewModel
//        val singleData = data as? ReviewModel
//        if (singleData != null) {
//            return listOf(singleData)
//        }
//
//        // Return an empty list if neither case matches
//        return emptyList()
//    }
//
//    suspend fun updateReview(id: Int, request: UpdateReviewRequest): ReviewModel {
//        return apiService.updateReview(id, request)
//    }
//
//    suspend fun deleteReview(request: DeleteReviewRequest): ReviewModel {
//        return apiService.deleteReview(request)
//    }
}
