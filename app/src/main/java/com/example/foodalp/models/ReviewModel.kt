package com.example.foodalp.models

// Response model for Review-related API calls
data class ReviewResponse(
    val data: List<ReviewModel> // If no review is found, data can be null
)

// Model for individual reviews
data class ReviewModel(
    val id: Int,
    val userId: Int,
    val restaurantId: Int,
    val content: String,
    val rating: Int,
    val createdAt: String // Use ISO 8601 string for compatibility with JSON dates
)

// Request model for creating a review
data class CreateReviewRequest(
    val userId: Int,
    val restaurantId: Int,
    val content: String,
    val rating: Int
)

// Request model for updating a review
data class UpdateReviewRequest(
    val id: Int,
    val content: String? = null, // Optional for partial updates
    val rating: Int? = null      // Optional for partial updates
)

// Request model for deleting a review
data class DeleteReviewRequest(
    val id: Int
)

// Request model for reading reviews
data class ReadReviewRequest(
    val id: Int? = null,          // Optional to fetch a specific review
    val restaurantId: Int? = null // Optional to fetch reviews by restaurant
)
