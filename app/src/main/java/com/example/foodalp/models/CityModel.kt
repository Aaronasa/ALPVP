package com.example.foodalp.models

// Response model for City-related API calls
data class CityResponse(
    val data: CityModel? // If no city is found, data can be null
)

// Model for individual cities
data class CityModel(
    val id: Int,
    val name: String,
    val image: String
)

// Request model for creating a city
data class CreateCityRequest(
    val name: String,
    val image: String
)

// Request model for updating a city
data class UpdateCityRequest(
    val id: Int,
    val name: String? = null,
    val image: String? = null
)

// Request model for deleting a city
data class DeleteCityRequest(
    val id: Int
)

// Request model for reading a city
data class ReadCityRequest(
    val id: Int? = null
)
