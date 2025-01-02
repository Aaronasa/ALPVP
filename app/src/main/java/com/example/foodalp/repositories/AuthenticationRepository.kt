package com.example.foodalp.repositories

import com.example.foodalp.models.LoginRequest
import com.example.foodalp.models.RegisterRequest
import com.example.foodalp.models.RegisterResponse
import com.example.foodalp.models.UserResponse
import com.example.foodalp.services.AuthenticationAPIService
import retrofit2.Response

// Interface for AuthenticationRepository
interface AuthenticationRepository {
    suspend fun register(username: String, email: String, password: String): Response<RegisterResponse>
    suspend fun login(email: String, password: String): Response<UserResponse>
}

// Network Authentication Repository implementation
class NetworkAuthenticationRepository(
    private val authenticationAPIService: AuthenticationAPIService
) : AuthenticationRepository {

    override suspend fun register(
        username: String,
        email: String,
        password: String
    ): Response<RegisterResponse> {
        // Prepare the registration request using RegisterRequest model
        val request = RegisterRequest(username, email, password)

        // Call the API to register the user
        return authenticationAPIService.registerUser(request)
    }

    override suspend fun login(
        email: String,
        password: String
    ): Response<UserResponse> {
        // Prepare the login request using LoginRequest model
        val request = LoginRequest(email, password)

        // Call the API to login the user
        return authenticationAPIService.loginUser(request)
    }
}
