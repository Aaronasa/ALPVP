package com.example.foodalp.uistates


import com.example.foodalp.models.RestaurantModel

data class RestaurantUIState(
    val isLoading: Boolean = false,
    val restaurants: List<RestaurantModel> = emptyList(),
    val errorMessage: String? = null
)
