package com.example.foodalp.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodalp.AppContainer
import com.example.foodalp.models.*
import com.example.foodalp.uiStates.UserStatusUIState
import com.example.foodalp.uiStates.UserUIState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UserViewModel : ViewModel() {

    // LiveData untuk User UI State
    private val _userState = MutableLiveData<UserUIState>()
    val userState: LiveData<UserUIState> get() = _userState

    // LiveData untuk User Status UI State
    private val _statusState = MutableLiveData<UserStatusUIState>()
    val statusState: LiveData<UserStatusUIState> get() = _statusState

    // API Services dari AppContainer
    private val authService = AppContainer.authService
    private val userService = AppContainer.userService

    // Register User
    fun registerUser(username: String, email: String, password: String) {
        if (username.isBlank() || email.isBlank() || password.isBlank()) {
            _statusState.postValue(UserStatusUIState.Error("All fields are required"))
            return
        }

        _statusState.value = UserStatusUIState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            try {
                Log.d("RegisterViewModel", "Sending request with: Username=$username, Email=$email, Password=$password")

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
    fun loginUser(request: LoginRequest) {
        _statusState.value = UserStatusUIState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = authService.loginUser(request)
                if (response.isSuccessful && response.body() != null) {
                    _userState.postValue(UserUIState.Success(response.body()!!))
                    _statusState.postValue(UserStatusUIState.Success)
                } else {
                    _statusState.postValue(UserStatusUIState.Error(response.message()))
                }
            } catch (e: Exception) {
                _statusState.postValue(UserStatusUIState.Error(e.localizedMessage ?: "Failed to login"))
            }
        }
    }

    // Get User
    fun getUser(id: Int?) {
        _userState.value = UserUIState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = authService.getUser(id)
                if (response.isSuccessful && response.body() != null) {
                    _userState.postValue(UserUIState.Success(response.body()!!))
                } else {
                    _userState.postValue(UserUIState.Error(response.message()))
                }
            } catch (e: Exception) {
                _userState.postValue(UserUIState.Error(e.localizedMessage ?: "Failed to fetch user"))
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
                }
            } catch (e: Exception) {
                _statusState.postValue(UserStatusUIState.Error(e.localizedMessage ?: "Failed to update user"))
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
                }
            } catch (e: Exception) {
                _statusState.postValue(UserStatusUIState.Error(e.localizedMessage ?: "Failed to delete user"))
            }
        }
    }

    // Logout User
    fun logoutUser(token: String) {
        _statusState.value = UserStatusUIState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = userService.logoutUser(token)
                if (response.isSuccessful) {
                    _statusState.postValue(UserStatusUIState.Success)
                } else {
                    _statusState.postValue(UserStatusUIState.Error(response.message()))
                }
            } catch (e: Exception) {
                _statusState.postValue(UserStatusUIState.Error(e.localizedMessage ?: "Failed to logout"))
            }
        }
    }
}
