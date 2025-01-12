package com.example.foodalp.uistates


import com.example.foodalp.models.FoodModel

sealed interface FoodByIdState {
    data class Success(val data: FoodModel): FoodByIdState
    object Start: FoodByIdState
    object Loading: FoodByIdState
    data class Failed(val errorMessage: String): FoodByIdState

}