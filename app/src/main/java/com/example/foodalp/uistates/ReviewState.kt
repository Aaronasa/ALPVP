package com.example.foodalp.uistates


import com.example.foodalp.models.ReviewModel


sealed interface ReviewState {
    data class Success(val data: List<ReviewModel>): ReviewState
    object Start: ReviewState
    object Loading: ReviewState
    data class Failed(val errorMessage: String): ReviewState
}