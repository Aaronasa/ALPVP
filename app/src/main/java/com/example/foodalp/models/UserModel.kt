// Response model for User-related API calls

data class UserModel(
    val id: Int,
    val username: String,
    val email: String,
    val token: String,
    val roleId: Int,
    val reviews: List<Review>?
)

data class UserResponse(
    val data: UserModel?,  // The user data you need
    val status: String,    // Optional: status of the response
    val message: String?   // Optional: error or success message
)

data class EmailRequest(
    val email: String
)

data class Review(
    val id: String,
    val content: String,
    val rating: Int
)

// Request model for login
data class LoginRequest(
    val email: String,
    val password: String
)

data class LoginResponse(
    val message: String,
    val data: UserModel
)

data class LogoutResponse(
    val message: String
)

data class DeleteResponse(
    val message: String
)

// Request model for registration
data class RegisterRequest(
    val email: String,
    val password: String,
    val username: String
)

// Request model for updating user
data class UpdateUserRequest(
    val username: String,
    val email: String
)

data class UpdateUserResponse(
    val message: String,
    val data: UserModel
)

// Register response model
data class RegisterResponse(
    val message: String,
    val data: UserData
)

// Model for user data in register response
data class UserData(
    val id: Int,
    val username: String,
    val email: String,
    val password: String, // Password disertakan hanya pada pendaftaran
    val token: String?,   // Token mungkin null pada awalnya
    val roleId: Int
)
