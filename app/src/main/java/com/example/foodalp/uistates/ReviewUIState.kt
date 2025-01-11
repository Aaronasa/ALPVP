package com.example.foodalp.uistates


import com.example.foodalp.models.ReviewModel

data class ReviewUIState(
    val isLoading: Boolean = false,
    val reviews: List<ReviewModel> = emptyList(),
    val errorMessage: String? = null
)
