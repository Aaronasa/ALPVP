package com.example.foodalp.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodalp.models.*
import com.example.foodalp.repositories.ReviewRepository
import com.example.foodalp.ui.state.ReviewUIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ReviewViewModel(private val repository: ReviewRepository) : ViewModel() {

    private val _uiState = MutableStateFlow(ReviewUIState())
    val uiState: StateFlow<ReviewUIState> get() = _uiState

    fun createReview(request: CreateReviewRequest) {
        viewModelScope.launch {
            try {
                _uiState.value = _uiState.value.copy(isLoading = true)
                val response = repository.createReview(request)
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    reviews = listOf(response)
                )
            } catch (e: Exception) {
                Log.e("ReviewViewModel", "Error creating review: ${e.message}")
                _uiState.value = _uiState.value.copy(isLoading = false, errorMessage = e.message)
            }
        }
    }

    fun getAllReviews() {
        viewModelScope.launch {
            try {
                _uiState.value = _uiState.value.copy(isLoading = true)

                // Get the response from the repository
                val response = repository.getAllReviews()

                // Safely handle response.data
                val reviewsList = response.data?.let { listOf(it) } ?: emptyList()

                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    reviews = reviewsList
                )
            } catch (e: Exception) {
                Log.e("ReviewViewModel", "Error fetching all reviews: ${e.message}")
                _uiState.value = _uiState.value.copy(isLoading = false, errorMessage = e.message)
            }
        }
    }

    fun getReviewsByRestaurant(restaurantId: Int) {
        viewModelScope.launch {
            try {
                _uiState.value = _uiState.value.copy(isLoading = true)
                val reviews = repository.getReviewsByRestaurant(restaurantId)
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    reviews = reviews // Populate reviews in the UI state
                )
            } catch (e: Exception) {
                Log.e("ReviewViewModel", "Error fetching reviews: ${e.message}")
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    errorMessage = e.message
                )
            }
        }
    }

    fun updateReview(id: Int, request: UpdateReviewRequest) {
        viewModelScope.launch {
            try {
                _uiState.value = _uiState.value.copy(isLoading = true)
                val response = repository.updateReview(id, request)
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    reviews = listOf(response)
                )
            } catch (e: Exception) {
                Log.e("ReviewViewModel", "Error updating review: ${e.message}")
                _uiState.value = _uiState.value.copy(isLoading = false, errorMessage = e.message)
            }
        }
    }

    fun deleteReview(request: DeleteReviewRequest) {
        viewModelScope.launch {
            try {
                _uiState.value = _uiState.value.copy(isLoading = true)
                val response = repository.deleteReview(request)
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    reviews = listOf(response)
                )
            } catch (e: Exception) {
                Log.e("ReviewViewModel", "Error deleting review: ${e.message}")
                _uiState.value = _uiState.value.copy(isLoading = false, errorMessage = e.message)
            }
        }
    }
}
