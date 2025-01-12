package com.example.foodalp.uistates


import com.example.foodalp.models.FoodModel

sealed interface FoodState {
    data class Success(val data: List<FoodModel>): FoodState
    object Start: FoodState
    object Loading: FoodState
    data class Failed(val errorMessage: String): FoodState
}
