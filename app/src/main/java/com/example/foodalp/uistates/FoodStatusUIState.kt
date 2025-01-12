package com.example.foodalp.uistates

sealed class FoodStatusUIState {
    object Idle : FoodStatusUIState() // Initial state or no actions
    object Loading : FoodStatusUIState() // When an action is in progress
    object Success : FoodStatusUIState() // When an action (e.g., update, delete) succeeds
    data class Error(val message: String) : FoodStatusUIState() // When an action fails
}