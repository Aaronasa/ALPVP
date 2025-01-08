package com.example.foodalp.models

// Response model for FoodRestaurant-related API calls
data class FoodRestaurantResponse(
    val data: FoodRestaurantModel? // If no record is found, data can be null
)

// Model for individual food-restaurant relationships
data class FoodRestaurantModel(
    val id: Int,
    val foodId: Int,
    val restaurantId: Int,
    val price: Double
)

// Request model for creating a food-restaurant relationship
data class CreateFoodRestaurantRequest(
    val foodId: Int,
    val restaurantId: Int,
    val price: Double
)

// Request model for updating a food-restaurant relationship
data class UpdateFoodRestaurantRequest(
    val id: Int,
    val foodId: Int? = null,
    val restaurantId: Int? = null,
    val price: Double? = null
)

// Request model for deleting a food-restaurant relationship
data class DeleteFoodRestaurantRequest(
    val id: Int
)

// Request model for reading a food-restaurant relationship
data class ReadFoodRestaurantRequest(
    val id: Int? = null
)
