// Response model for User-related API calls
data class UserResponse(
    val data: UserModel
)

data class UserModel(
    val id: Int,
    val username: String,
    val email: String,
    val token: String,
    val roleId: Int,
    val reviews: List<Review>?
)

data class EmailRequest(val email: String)

data class Review(
    val id: String,
    val content: String,
    val rating: Int
)

// Role model representing the user's role
data class RoleModel(
    val id: Int,
    val name: String
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

// Request model for deleting a user
data class DeleteUserRequest(
    val id: Int
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
