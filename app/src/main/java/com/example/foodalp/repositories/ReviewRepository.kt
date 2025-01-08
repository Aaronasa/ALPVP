package com.example.foodalp.repositories

import android.util.Log
import com.example.foodalp.models.*
import com.example.foodalp.services.ReviewAPIService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import retrofit2.HttpException

class ReviewRepository(private val apiService: ReviewAPIService) {

    suspend fun getAllReviews(token: String): List<ReviewModel> =
        withContext(Dispatchers.IO) {
            try {
                // Log request details (optional, useful for debugging)
                Log.d("Repository", "Fetching all Reviews...")
                // Make the API call
                val Review = apiService.getAllReviews(token)
                // Log success response (optional)
                Log.d("Repository", "Successfully fetched Reviews: ${Review.data}")
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
                Log.e("Repository", "Error fetching Reviews: ${e.message}", e)
                throw e
            }
        }


    suspend fun createReview(token: String, request: CreateReviewRequest): ReviewModel {
        Log.d("ReviewRepository", "Preparing review request for: $request")

        // Prepare RequestBody parts for each field
        val userIdPart = request.userId?.toString()?.let {
            RequestBody.create("text/plain".toMediaTypeOrNull(), it)
        }
        val restaurantIdPart = request.restaurantId?.toString()?.let {
            RequestBody.create("text/plain".toMediaTypeOrNull(), it)
        }
        val contentPart = RequestBody.create("text/plain".toMediaTypeOrNull(), request.content)
        val ratingPart = RequestBody.create("text/plain".toMediaTypeOrNull(), request.rating.toString())

        Log.d("ReviewRepository", "User ID Part: $userIdPart")
        Log.d("ReviewRepository", "Restaurant ID Part: $restaurantIdPart")
        Log.d("ReviewRepository", "Content Part: $contentPart")
        Log.d("ReviewRepository", "Rating Part: $ratingPart")

        // Send the request using the API service
        return apiService.creatReviews(
            token = token,
            userId = userIdPart!!,
            restaurantId = restaurantIdPart!!,
            content = contentPart,
            rating = ratingPart
        )
    }

}


//    suspend fun updateReview(id: Int, request: UpdateReviewRequest): ReviewModel {
//        return apiService.updateReview(id, request)
//    }
//
//    suspend fun deleteReview(request: DeleteReviewRequest): ReviewModel {
//        return apiService.deleteReview(request)
//    }

