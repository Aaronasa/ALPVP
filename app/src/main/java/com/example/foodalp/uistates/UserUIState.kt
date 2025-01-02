package com.example.foodalp.uiStates

import com.example.foodalp.models.UserResponse

sealed class UserUIState {
    object Idle : UserUIState() // Initial state or no actions
    object Loading : UserUIState() // When data is being fetched
    data class Success(val user: UserResponse) : UserUIState() // When data is successfully retrieved
    data class Error(val message: String) : UserUIState() // When an error occurs
}
