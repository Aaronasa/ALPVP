package com.example.foodalp.repositories

import LoginRequest
import RegisterRequest
import com.example.foodalp.services.AuthenticationAPIService
import retrofit2.Response

// Interface for AuthenticationRepository
interface AuthenticationRepository {
    suspend fun register(username: String, email: String, password: String): Response<RegisterResponse>
    suspend fun login(email: String, password: String): Response<LoginResponse>
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
        // Validate input
        if (username.isBlank() || email.isBlank() || password.isBlank()) {
            throw IllegalArgumentException("All fields must be filled")
        }

        // Prepare the registration request
        val request = RegisterRequest(username, email, password)

        return try {
            // Call the API to register the user
            authenticationAPIService.registerUser(request)
        } catch (e: Exception) {
            throw Exception("Failed to register: ${e.localizedMessage}", e)
        }
    }

    override suspend fun login(
        email: String,
        password: String
    ): Response<LoginResponse> {
        // Validate input
        if (email.isBlank() || password.isBlank()) {
            throw IllegalArgumentException("Email and password are required")
        }

        // Prepare the login request
        val request = LoginRequest(email, password)

        return try {
            // Call the API to login the user
            authenticationAPIService.loginUser(request)
        } catch (e: Exception) {
            throw Exception("Failed to login: ${e.localizedMessage}", e)
        }
    }
}
