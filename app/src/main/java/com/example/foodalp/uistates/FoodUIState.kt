package com.example.foodalp.uistates


import com.example.foodalp.models.FoodModel

data class FoodUIState(
    val isLoading: Boolean = false,
    val foods: List<FoodModel> = emptyList(),
    val errorMessage: String? = null
)
