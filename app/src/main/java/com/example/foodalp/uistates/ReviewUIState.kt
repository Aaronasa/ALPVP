package com.example.foodalp.ui.state

import com.example.foodalp.models.ReviewModel

data class ReviewUIState(
    val isLoading: Boolean = false,
    val reviews: List<ReviewModel> = emptyList(),
    val errorMessage: String? = null
)
