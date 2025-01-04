package com.example.foodalp.models

// Response model for Category-related API calls
data class CategoryResponse(
    val data: CategoryModel? // If no category is found, data can be null
)

// Model for individual categories
data class CategoryModel(
    val id: Int,
    val name: String
)

// Request model for creating a category
data class CreateCategoryRequest(
    val name: String
)

// Request model for updating a category
data class UpdateCategoryRequest(
    val id: Int,
    val name: String? = null
)

// Request model for deleting a category
data class DeleteCategoryRequest(
    val id: Int
)

// Request model for reading a category
data class ReadCategoryRequest(
    val id: Int? = null
)
