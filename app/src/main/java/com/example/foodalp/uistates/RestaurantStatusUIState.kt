package com.example.foodalp.uiStates

sealed class RestaurantStatusUIState {
    object Idle : RestaurantStatusUIState() // Initial state or no actions
    object Loading : RestaurantStatusUIState() // When an action is in progress
    object Success : RestaurantStatusUIState() // When an action (e.g., update, delete) succeeds
    data class Error(val message: String) : RestaurantStatusUIState() // When an action fails
}
