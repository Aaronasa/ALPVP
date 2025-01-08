package com.example.foodalp.models

// Response model for Food-related API calls
data class FoodResponse(
    val data: FoodModel? // If no food is found, data can be null
)

// Model for individual foods
data class FoodModel(
    val id: Int,
    val name: String,
    val description: String,
    val ingredients: String,
    val image: String,
    val categoryId: Int,
    val cityId: Int
)

// Request model for creating food
data class CreateFoodRequest(
    val name: String,
    val description: String,
    val ingredients: String,
    val image: String,
    val categoryId: Int,
    val cityId: Int
)

// Request model for updating food
data class UpdateFoodRequest(
    val id: Int,
    val name: String? = null,
    val description: String? = null,
    val ingredients: String? = null,
    val image: String? = null,
    val categoryId: Int? = null,
    val cityId: Int? = null
)

// Request model for deleting food
data class DeleteFoodRequest(
    val id: Int
)

// Request model for reading food
data class ReadFoodRequest(
    val id: Int? = null
)
