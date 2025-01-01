package com.example.foodalp.services


import com.example.foodalp.models.LoginRequest
import com.example.foodalp.models.RegisterRequest
import com.example.foodalp.models.RegisterResponse
import com.example.foodalp.models.UpdateUserRequest
import com.example.foodalp.models.UserResponse
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Query

interface AuthenticationAPIService {
//    @POST("api/create")
//    fun registerUser(
//        @Body registerMap: HashMap<String, String>
//    ): Call <UserResponse>
//
//    @POST("api/login")
//    fun loginUser(
//        @Body loginMap: HashMap<String, String>
//    ): Call <UserResponse>

    // Create User (Register)
        @POST("create")  // Make sure this matches your API endpoint
        suspend fun registerUser(@Body request: RegisterRequest): Response<RegisterResponse>

//    // Read User
//    @GET("read")
//    suspend fun getUser Response<UserResponse>

    // Update User
    @PUT("update")
    suspend fun updateUser(@Body request: UpdateUserRequest): Response<UserResponse>

    // Delete User
    @DELETE("delete")
    suspend fun deleteUser(@Query("id") id: Int): Response<UserResponse>

    // Login User
    @POST("login")
    suspend fun loginUser(@Body request: LoginRequest): Response<UserResponse>

}