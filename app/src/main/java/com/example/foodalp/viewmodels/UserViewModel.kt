package com.example.foodalp.viewmodels

import LoginRequest
import RegisterRequest
import UpdateUserRequest
import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodalp.AppContainer
import com.example.foodalp.models.*
import com.example.foodalp.repositories.UserRepository
import com.example.foodalp.uiStates.UserStatusUIState
import com.example.foodalp.uiStates.UserUIState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UserViewModel : ViewModel() {

    // LiveData for User UI State
    private val _userState = MutableLiveData<UserUIState>()
    val userState: LiveData<UserUIState> get() = _userState

    // LiveData for User Status UI State
    private val _statusState = MutableLiveData<UserStatusUIState>()
    val statusState: LiveData<UserStatusUIState> get() = _statusState

    // API Services from AppContainer
    private val authService = AppContainer.authService

    // Register User
    fun registerUser(username: String, email: String, password: String) {
        if (username.isBlank() || email.isBlank() || password.isBlank()) {
            _statusState.postValue(UserStatusUIState.Error("All fields are required"))
            return
        }

        _statusState.value = UserStatusUIState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val request = RegisterRequest(email, password, username)
                val response = authService.registerUser(request)

                if (response.isSuccessful) {
                    val registerResponse = response.body()
                    if (registerResponse != null) {
                        Log.d("RegisterViewModel", "Registration successful: ${registerResponse.message}")
                        _statusState.postValue(UserStatusUIState.Success)
                    } else {
                        Log.e("RegisterViewModel", "Error: Response body is null")
                        _statusState.postValue(UserStatusUIState.Error("Response body is null"))
                    }
                } else {
                    Log.e("RegisterViewModel", "Error: ${response.code()}, Message: ${response.message()}")
                    _statusState.postValue(UserStatusUIState.Error("Error: ${response.code()}, Message: ${response.message()}"))
                }
            } catch (e: Exception) {
                Log.e("RegisterViewModel", "Error during registration: ${e.localizedMessage}")
                _statusState.postValue(UserStatusUIState.Error(e.localizedMessage ?: "Failed to register user"))
            }
        }
    }

    // Login User
    fun loginUser(email: String, password: String) {
        if (email.isBlank() || password.isBlank()) {
            _statusState.postValue(UserStatusUIState.Error("Email and password are required"))
            return
        }

        _statusState.value = UserStatusUIState.Loading

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val request = LoginRequest(email, password)
                val response = authService.loginUser(request)

                withContext(Dispatchers.Main) {
                    if (response.isSuccessful) {
                        val loginResponse = response.body()

                        // Log the entire response for debugging
                        Log.d("LoginResponse", "Response body: $loginResponse")

                        // Check if loginResponse is not null
                        if (loginResponse != null) {
                            val token = loginResponse.data.token
                            val user = loginResponse.data

                            // Check if the user is null
                            if (user != null) {
                                saveUserSession(token)
                                _userState.postValue(UserUIState.Success(user))  // User successfully logged in
                                _statusState.postValue(UserStatusUIState.Success)
                                _statusState.value = UserStatusUIState.Success
                            } else {
                                _statusState.postValue(UserStatusUIState.Error("User data is null"))
                                Log.e("LoginViewModel", "Error: User data is null")
                            }
                        } else {
                            _statusState.postValue(UserStatusUIState.Error("Response body is null"))
                            Log.e("LoginViewModel", "Error: Response body is null")
                        }
                    } else {
                        _statusState.postValue(
                            UserStatusUIState.Error("Error: ${response.code()}, Message: ${response.message()}")
                        )
                        Log.e("LoginViewModel", "Error: ${response.code()} - ${response.message()}")
                    }
                }
            } catch (e: Exception) {
                _statusState.postValue(UserStatusUIState.Error(e.localizedMessage ?: "Login failed"))
                Log.e("LoginViewModel", "Error during login: ${e.localizedMessage}")
                e.printStackTrace()
            }
        }
    }

    // Update User
    fun updateUser(request: UpdateUserRequest) {
        _statusState.value = UserStatusUIState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = authService.updateUser(request)
                if (response.isSuccessful) {
                    _statusState.postValue(UserStatusUIState.Success)
                } else {
                    _statusState.postValue(UserStatusUIState.Error(response.message()))
                    Log.e("UpdateViewModel", "Error updating user: ${response.message()}")
                }
            } catch (e: Exception) {
                _statusState.postValue(UserStatusUIState.Error(e.localizedMessage ?: "Failed to update user"))
                Log.e("UpdateViewModel", "Error during update: ${e.localizedMessage}")
            }
        }
    }

    // Delete User
    fun deleteUser(id: Int) {
        _statusState.value = UserStatusUIState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = authService.deleteUser(id)
                if (response.isSuccessful) {
                    _statusState.postValue(UserStatusUIState.Success)
                } else {
                    _statusState.postValue(UserStatusUIState.Error(response.message()))
                    Log.e("DeleteViewModel", "Error deleting user: ${response.message()}")
                }
            } catch (e: Exception) {
                _statusState.postValue(UserStatusUIState.Error(e.localizedMessage ?: "Failed to delete user"))
                Log.e("DeleteViewModel", "Error during delete: ${e.localizedMessage}")
            }
        }
    }

    // Logout User (Remove Token)
    fun logoutUser() {
        _statusState.value = UserStatusUIState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            try {
                clearUserSession() // Clear session
                _statusState.postValue(UserStatusUIState.Success)
            } catch (e: Exception) {
                _statusState.postValue(UserStatusUIState.Error(e.localizedMessage ?: "Failed to logout"))
                Log.e("LogoutViewModel", "Error during logout: ${e.localizedMessage}")
            }
        }
    }

    // Save the token into SharedPreferences
    private fun saveUserSession(token: String) {
        val editor = AppContainer.sharedPreferences.edit()
        editor.putString("USER_TOKEN", token)
        editor.apply()
        Log.d("UserViewModel", "Token saved: $token")
    }

    // Clear the token from SharedPreferences
    private fun clearUserSession() {
        val editor = AppContainer.sharedPreferences.edit()
        editor.remove("USER_TOKEN")
        editor.apply()
        Log.d("UserViewModel", "Token cleared")
    }
}
