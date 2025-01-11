package com.example.foodalp.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodalp.AppContainer
import com.example.foodalp.models.*
import com.example.foodalp.repositories.RestaurantRepository
import com.example.foodalp.repositories.ReviewRepository
import com.example.foodalp.uistates.ReviewState
import com.example.foodalp.uistates.ReviewUIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ReviewViewModel() : ViewModel() {

    private val repository = ReviewRepository(AppContainer.reviewService)
    private val _uiState = MutableStateFlow(ReviewUIState())
    val uiState: StateFlow<ReviewUIState>
        get() {
            return _uiState.asStateFlow()
        }

    private val _Review = MutableStateFlow<List<ReviewModel>>(emptyList())
    val Review: StateFlow<List<ReviewModel>> = _Review

    private val _admin = MutableStateFlow<List<ReviewModel>>(emptyList())
    val admin: StateFlow<List<ReviewModel>> = _admin

    private val _UIState = MutableStateFlow<ReviewState>(ReviewState.Start)
    val UIstate : StateFlow<ReviewState> = _UIState

    private val _ReviewById = MutableStateFlow<ReviewModel?>(null)
    val ReviewById : StateFlow<ReviewModel?> = _ReviewById

    fun fetchAllReviews(token: String) {
        viewModelScope.launch {
            _UIState.value = ReviewState.Loading
            try {
                val FetchReview = repository.getAllReviews(token)
                Log.d("ViewModel", "Fetched reviews: $FetchReview")
                _Review.value = FetchReview
                _UIState.value = ReviewState.Success(FetchReview)
            } catch (e: Exception) {
                _UIState.value = ReviewState.Failed(e.message ?: "Unknown error")
            }
        }
    }

    fun fetchAllReviewsAdmin(token: String) {
        viewModelScope.launch {
            _UIState.value = ReviewState.Loading
            try {
                val FetchReview = repository.getAllReviewsAdmin(token)
                Log.d("ViewModel", "Fetched reviews: $FetchReview")
                _admin.value = FetchReview
                _UIState.value = ReviewState.Success(FetchReview)
            } catch (e: Exception) {
                _UIState.value = ReviewState.Failed(e.message ?: "Unknown error")
            }
        }
    }

    fun fetchReviewById(token: String, reviewId: Int, callback: (ReviewModel?) -> Unit) {
        viewModelScope.launch {
            _UIState.value = ReviewState.Loading
            try{
                val response = repository.getReviewById(token, reviewId)
                Log.d("ViewModel", "Fetched review: $response")
                _ReviewById.value = response
                _UIState.value = ReviewState.Success(listOf(response!!))
                callback(response)
            }catch (e: Exception){
                Log.e("ViewModel", "Error fetching review: ${e.message}", e)
                _UIState.value = ReviewState.Failed(e.message ?: "Unknown error")
                callback(null)
            }
        }
    }

    fun fetchReviewByIdAdmin(token: String, reviewId: Int, callback: (ReviewModel?) -> Unit) {
        viewModelScope.launch {
            _UIState.value = ReviewState.Loading
            try{
                val response = repository.getReviewByIdAdmin(token, reviewId)
                Log.d("ViewModel", "Fetched review: $response")
                _ReviewById.value = response
                _UIState.value = ReviewState.Success(listOf(response!!))
                callback(response)
            }catch (e: Exception){
                Log.e("ViewModel", "Error fetching review: ${e.message}", e)
                _UIState.value = ReviewState.Failed(e.message ?: "Unknown error")
                callback(null)
            }
        }
    }

    fun createReview(token: String, userId: Int, restaurantId: Int, content: String, rating: Int){
        viewModelScope.launch {
            try{

                repository.createReview(token, userId, restaurantId, content, rating)
                Log.d("ViewModel", "Review created successfully")
                fetchAllReviews(token)
                _uiState.value = _uiState.value.copy(errorMessage = null)
            }catch (e: Exception){
                Log.e("ViewModel", "Error creating review: ${e.message}")
                _uiState.value = _uiState.value.copy(errorMessage = e.message)
            }
        }
    }

    fun createReviewAdmin(token: String, userId: Int, restaurantId: Int, content: String, rating: Int){
        viewModelScope.launch {
            try{

                repository.createReviewAdmin(token, userId, restaurantId, content, rating)
                Log.d("ViewModel", "Review created successfully")
                fetchAllReviewsAdmin(token)
                _uiState.value = _uiState.value.copy(errorMessage = null)
            }catch (e: Exception){
                Log.e("ViewModel", "Error creating review: ${e.message}")
                _uiState.value = _uiState.value.copy(errorMessage = e.message)
            }
        }
    }

    fun updateReview(token: String, id: Int, content: String, rating: Int){
        viewModelScope.launch {
            try {
                repository.updateReview(token, id, UpdateReviewRequest(content, rating))
                fetchAllReviews(token)
            }catch (e: Exception){
                _uiState.value = _uiState.value.copy(errorMessage = e.message)
            }
        }
    }

    fun updateReviewAdmin(token: String, id: Int, content: String, rating: Int){
        viewModelScope.launch {
            try {
                repository.updateReviewAdmin(token, id, UpdateReviewRequest(content, rating))
                fetchAllReviewsAdmin(token)
            }catch (e: Exception){
                _uiState.value = _uiState.value.copy(errorMessage = e.message)
            }
        }
    }

    fun deleteReview(token: String, id: Int){
        viewModelScope.launch {
            try {
                Log.d("ReviewViewModel", "Deleting review with ID: $id")
                repository.deleteReview(token, id)
                fetchAllReviews(token)
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(errorMessage = e.message)
            }
        }
    }

    fun deleteReviewAdmin(token: String, id: Int){
        viewModelScope.launch {
            try {
                Log.d("ReviewViewModel", "Deleting review with ID: $id")
                repository.deleteReviewAdmin(token, id)
                fetchAllReviewsAdmin(token)
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(errorMessage = e.message)
            }
        }
    }


    fun fetchReviewsByRestaurant(token: String, restaurantId: Int) {
        viewModelScope.launch {
            _UIState.value = ReviewState.Loading
            Log.d("Review View Model", "Fetching reviews for restaurant ID: $restaurantId")
            try {
                val FetchReview = repository.getReviewsByRestaurant(token, restaurantId)
                Log.d("ViewModel", "Fetched reviews: $FetchReview")
                _Review.value = FetchReview
                _UIState.value = ReviewState.Success(FetchReview)
            } catch (e: Exception) {
                _UIState.value = ReviewState.Failed(e.message ?: "Unknown error")
            }
        }
    }

}
