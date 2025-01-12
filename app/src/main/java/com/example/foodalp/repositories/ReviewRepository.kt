package com.example.foodalp.repositories

import android.util.Log
import com.example.foodalp.AppContainer
import com.example.foodalp.models.*
import com.example.foodalp.services.ReviewAPIService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import retrofit2.HttpException
import retrofit2.Response


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

    suspend fun getAllReviewsAdmin(token: String): List<ReviewModel> =
        withContext(Dispatchers.IO) {
            try {
                // Log request details (optional, useful for debugging)
                Log.d("Repository", "Fetching all Reviews...")
                // Make the API call
                val Review = apiService.getAllReviewsAdmin(token)
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

    suspend fun getReviewById(token: String, id: Int): ReviewModel? =
        withContext(Dispatchers.IO){
            try {
                val response = AppContainer.reviewService.getReviewById(token, id)
                if (response.isSuccessful){
                    val apiResponse = response.body()
                    Log.d("ReviewService", "Raw Response Body: $apiResponse")
                    apiResponse?.data
                }else{
                    val errorMessage = response.errorBody()?.string()
                    Log.e("ReviewService", "Error Response: $errorMessage")
                    throw Exception("API Error: $errorMessage")
                }
            } catch (e: Exception){
                Log.e("ReviewRepository", "Error fetching Review: ${e.message}", e)
                throw e
            }
        }

    suspend fun getReviewByIdAdmin(token: String, id: Int): ReviewModel? =
        withContext(Dispatchers.IO){
            try {
                val response = AppContainer.reviewService.getReviewByIdAdmin(token, id)
                if (response.isSuccessful){
                    val apiResponse = response.body()
                    Log.d("ReviewService", "Raw Response Body: $apiResponse")
                    apiResponse?.data
                }else{
                    val errorMessage = response.errorBody()?.string()
                    Log.e("ReviewService", "Error Response: $errorMessage")
                    throw Exception("API Error: $errorMessage")
                }
            } catch (e: Exception){
                Log.e("ReviewRepository", "Error fetching Review: ${e.message}", e)
                throw e
            }
        }

    suspend fun createReview( token: String, userId: Int, restaurantId: Int, content: String, rating: Int ): ReviewModel{
        val request = CreateReviewRequest(userId, restaurantId, content, rating)
        return apiService.creatReviews(token, request)
    }

    suspend fun createReviewAdmin( token: String, userId: Int, restaurantId: Int, content: String, rating: Int ): ReviewModel{
        val request = CreateReviewRequest(userId, restaurantId, content, rating)
        return apiService.creatReviewsAdmin(token, request)
    }

    suspend fun updateReview(token: String, id:Int,  request: UpdateReviewRequest): ReviewModel =
        apiService.updateReview(token, id,  request)

    suspend fun updateReviewAdmin(token: String, id:Int,  request: UpdateReviewRequest): ReviewModel =
        apiService.updateReviewAdmin(token, id,  request)

    suspend fun deleteReview(token: String, id: Int): ReviewModel =
        apiService.deleteReview(token, id)

    suspend fun deleteReviewAdmin(token: String, id: Int): ReviewModel =
        apiService.deleteReviewAdmin(token, id)



    suspend fun getReviewsByRestaurant(token: String, restaurantId: Int): List<ReviewModel> =
        withContext(Dispatchers.IO) {
            Log.d("ReviewRepository", "Fetching Reviews for Restaurant ID: $restaurantId")
            try {
                val response = AppContainer.reviewService.getReviewsByRestaurant(token, restaurantId)
                response.data
            } catch (e: Exception) {
                Log.e("ReviewRepository", "Error fetching Reviews: ${e.message}", e)
                throw e
            }
        }


}

