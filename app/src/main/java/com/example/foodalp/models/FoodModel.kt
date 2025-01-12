package com.example.foodalp.models

// Response model for Food-related API calls
data class FoodResponse(
    val data: FoodModel? // If no record is found, data can be null
)

// Model for individual food data
data class FoodModel(
    val id: Int,
    val name: String,
    val description: String,
    val ingredients: String,
    val image: String,
    val categoryId: Int,
    val cityId: Int
)

// Request model for creating a food
data class CreateFoodRequest(
    val name: String,
    val description: String,
    val ingredients: String,
    val image: String,
    val categoryId: Int,
    val cityId: Int
)

// Response model for creating a food
data class CreateFoodResponse(
    val success: Boolean,
    val message: String,
    val data: FoodModel // Contains the newly created food record
)

// Request model for updating a food
data class UpdateFoodRequest(
    val id: Int,
    val name: String? = null,
    val description: String? = null,
    val ingredients: String? = null,
    val image: String? = null,
    val categoryId: Int? = null,
    val cityId: Int? = null
)

// Response model for fetching all foods
data class GetAllFoodsResponse(
    val data: List<FoodModel> // List of food records
)
