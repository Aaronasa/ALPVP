package com.example.foodalp.viewmodels

import LoginRequest
import RegisterRequest
import EmailRequest
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
import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

class UserViewModel : ViewModel() {

    // LiveData for User UI State
    private val _userState = MutableLiveData<UserUIState>()
    val userState: LiveData<UserUIState> get() = _userState

    // LiveData for User Status UI State
    private val _statusState = MutableLiveData<UserStatusUIState>()
    val statusState: LiveData<UserStatusUIState> get() = _statusState

    // API Services from AppContainer
    private val authService = AppContainer.authService

    // Helper function to retrieve the token
    private fun getUserToken(): String? {
        return AppContainer.sharedPreferences.getString("USER_TOKEN", null)
    }

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
    fun loginUser(email: String, password: String, context: Context) {
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

                        if (loginResponse != null) {
                            val token = loginResponse.data.token
                            val user = loginResponse.data

                            if (user != null) {
                                // Save the token and email in SharedPreferences (or another storage method)
                                saveUserSession(context, token, email)

                                // After login, pass both the token and email to loadUserData
                                loadUserData(token, email)

                                _userState.postValue(UserUIState.Success(user))
                                _statusState.postValue(UserStatusUIState.Success)
                                Log.d("UserViewModel", "User logged in: $user")
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
            }
        }
    }



    // Update User (using token from shared preferences)
    fun updateUser(request: UpdateUserRequest) {
        val token = getUserToken()
        if (token.isNullOrEmpty()) {
            _statusState.postValue(UserStatusUIState.Error("User not logged in"))
            return
        }

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
        val token = getUserToken()
        if (token.isNullOrEmpty()) {
            _statusState.postValue(UserStatusUIState.Error("User not logged in"))
            return
        }

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

    // Save user session
    // Save session data in SharedPreferences
    fun saveUserSession(context: Context, token: String, email: String) {
        val sharedPreferences = context.getSharedPreferences("user_session", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("token", token)
        editor.putString("email", email)
        editor.apply()
    }


    // Clear user session (on logout)
    fun clearUserSession() {
        val editor = AppContainer.sharedPreferences.edit()
        editor.remove("USER_TOKEN")
        editor.remove("USER_EMAIL")
        editor.apply()
        Log.d("UserViewModel", "Token and Email cleared")
    }

    // Load User Data (get user information using the token)
    fun loadUserData(token: String, email: String) {
        if (token.isNullOrEmpty()) {
            _userState.postValue(UserUIState.Error("User not logged in"))
            return
        }

        _userState.value = UserUIState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            try {
                // Assuming you need to pass email to the service
                val emailRequest = EmailRequest(email)

                // Make API call and get response
                val response = authService.getUserData(token, emailRequest)

                withContext(Dispatchers.Main) {
                    if (response.isSuccessful) {
                        val userResponse = response.body()

                        // Check if the response body is not null
                        if (userResponse != null) {
                            val user = userResponse.data  // Extract the UserModel from the response

                            if (user != null) {
                                _userState.postValue(UserUIState.Success(user))  // Pass UserModel
                            } else {
                                _userState.postValue(UserUIState.Error("User data is null"))
                            }
                        } else {
                            _userState.postValue(UserUIState.Error("Failed to load user data after success"))
                        }
                    } else {
                        _userState.postValue(UserUIState.Error("Failed to load user data response fail"))
                        Log.e("UserViewModel", "Error loading user data: ${response.message()}")
                    }
                }
            } catch (e: Exception) {
                _userState.postValue(UserUIState.Error(e.localizedMessage ?: "Failed to load user data in email and token"))
                Log.e("UserViewModel", "Error loading user data: ${e.localizedMessage}")
            }
        }
    }




}



