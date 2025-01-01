package com.example.foodalp.repositories

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.foodalp.models.GeneralResponseModel
import com.example.foodalp.models.UserResponse
import com.example.foodalp.services.UserAPIService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import retrofit2.Response

interface UserRepository {
    val currentUserToken: Flow<String>
    val currentUsername: Flow<String>

    suspend fun logout(token: String): Response<UserResponse>  // Use suspend for the logout method

    suspend fun saveUserToken(token: String)
    suspend fun saveUsername(username: String)
}

class NetworkUserRepository(
    private val userDataStore: DataStore<Preferences>,
    private val userAPIService: UserAPIService
) : UserRepository {
    private companion object {
        val USER_TOKEN = stringPreferencesKey("token")
        val USERNAME = stringPreferencesKey("username")
    }

    // Provides the stored token from DataStore
    override val currentUserToken: Flow<String> = userDataStore.data.map { preferences ->
        preferences[USER_TOKEN] ?: "Unknown"
    }

    // Provides the stored username from DataStore
    override val currentUsername: Flow<String> = userDataStore.data.map { preferences ->
        preferences[USERNAME] ?: "Unknown"
    }

    // Saves the user token to DataStore
    override suspend fun saveUserToken(token: String) {
        userDataStore.edit { preferences ->
            preferences[USER_TOKEN] = token
        }
    }

    // Logs out the user by calling the API and passing the token in the header
    override suspend fun logout(token: String): Response<UserResponse> {
        return userAPIService.logoutUser(token)  // Use suspend function here
    }

    // Saves the username to DataStore
    override suspend fun saveUsername(username: String) {
        userDataStore.edit { preferences ->
            preferences[USERNAME] = username
        }
    }
}
