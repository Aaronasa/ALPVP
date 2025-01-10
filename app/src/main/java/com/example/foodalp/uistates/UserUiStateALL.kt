package com.example.foodalp.uistates

class UserUiStateALL {
    sealed class UserUIState {
        object Idle : UserUIState() // Initial state or no actions
        object Loading : UserUIState() // When data is being fetched
        data class Success(val user: UserModel) : UserUIState() // When data is successfully retrieved
        data class Error(val message: String) : UserUIState() // When an error occurs
    }
}