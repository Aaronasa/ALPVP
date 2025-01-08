package com.example.foodalp.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodalp.AppContainer
import com.example.foodalp.models.*
import com.example.foodalp.repositories.RestaurantRepository
import com.example.foodalp.repositories.ReviewRepository
import com.example.foodalp.ui.state.RestaurantUIState
import com.example.foodalp.ui.state.ReviewUIState
import com.example.foodalp.uistates.ReviewState
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
    val Review = _Review

    private val _UIState = MutableStateFlow<ReviewState>(ReviewState.Start)
    val UIstate : StateFlow<ReviewState> = _UIState

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

    fun createReview(token: String, request: CreateReviewRequest){
        viewModelScope.launch {
            try{
                repository.createReview(token, request)
                Log.d("ViewModel", "Review created successfully")
                fetchAllReviews(token)
                _uiState.value = _uiState.value.copy(errorMessage = null)
            }catch (e: Exception){
                Log.e("ViewModel", "Error creating review: ${e.message}")
                _uiState.value = _uiState.value.copy(errorMessage = e.message)
            }
        }
    }


}
