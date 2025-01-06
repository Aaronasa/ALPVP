package com.example.foodalp.models

import com.google.gson.annotations.SerializedName

// Main response for food-related operations
data class FoodResponse(
    @SerializedName("data")
    val data: FoodModel? // Null if no food found
)

// Individual food data model
data class FoodModel(
    @SerializedName("id")
    val id: Int,

    @SerializedName("name")
    val name: String,

    @SerializedName("description")
    val description: String,

    @SerializedName("ingredients")
    val ingredients: String,

    @SerializedName("image")
    val image: String,

    @SerializedName("categoryId")
    val categoryId: Int,

    @SerializedName("cityId")
    val cityId: Int
)

// Request model to create food
data class CreateFoodRequest(
    @SerializedName("name")
    val name: String,

    @SerializedName("description")
    val description: String,

    @SerializedName("ingredients")
    val ingredients: String,

    @SerializedName("image")
    val image: String,

    @SerializedName("categoryId")
    val categoryId: Int,

    @SerializedName("cityId")
    val cityId: Int
)
data class CreateFoodResponse(
    @SerializedName("success")
    val success: Boolean,

    @SerializedName("message")
    val message: String,

    @SerializedName("data")
    val food: FoodModel // Or whatever the response data looks like
)

// Request model to update food
data class UpdateFoodRequest(
    @SerializedName("id")
    val id: Int,

    @SerializedName("name")
    val name: String? = null,

    @SerializedName("description")
    val description: String? = null,

    @SerializedName("ingredients")
    val ingredients: String? = null,

    @SerializedName("image")
    val image: String? = null,

    @SerializedName("categoryId")
    val categoryId: Int? = null,

    @SerializedName("cityId")
    val cityId: Int? = null
)

// Response for all foods (from "readall" endpoint)
data class GetAllFoodsResponse(
    @SerializedName("data")
    val data: List<FoodModel>
)
