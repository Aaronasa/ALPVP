package com.example.foodalp.models

// Response model for User-related API calls
data class UserResponse(
    val message: String,
    val data: UserModel?
)

// User data model that contains user details
data class UserModel(
    val id: Int,
    val username: String,
    val email: String,
    val token: String?, // The token is used for login and authentication
    val role: RoleModel
)

// Role model representing the user's role
data class RoleModel(
    val id: Int,
    val name: String
)

// Request model for login, to send email and password
data class LoginRequest(
    val email: String,
    val password: String
)

// Request model for registration, to send username, email, and password
data class RegisterRequest(
    val email: String,
    val password: String,
    val username: String
)

// Request model for updating user, to send new username and email
data class UpdateUserRequest(
    val username: String,
    val email: String
)

// Request model for deleting a user, contains user ID
data class DeleteUserRequest(
    val id: Int
)

data class RegisterResponse(
    val message: String,
    val data: UserData
)

data class UserData(
    val id: Int,
    val username: String,
    val email: String,
    val password: String,  // You might not need to store the password in the response, but it's included here.
    val token: String?,    // Token might be null initially if not provided.
    val roleId: Int
)
