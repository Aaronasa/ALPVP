package com.example.foodalp.repositories

import android.content.SharedPreferences
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.foodalp.services.UserAPIService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

// UserRepository Interface (without constructor)
interface UserRepository {
    val currentUserToken: Flow<String>
    val currentUsername: Flow<String>

    suspend fun saveUserToken(token: String)
    suspend fun saveUsername(username: String)

    suspend fun saveUserSession(token: String)  // Make this suspend as well if you plan to call it from a coroutine scope
    suspend fun clearSession()
    fun isUserLoggedIn(): Boolean
}

// NetworkUserRepository Implementation
class NetworkUserRepository(
    private val userDataStore: DataStore<Preferences>,
    userAPIService: UserAPIService,
    private val preferences: SharedPreferences // Pass SharedPreferences to the constructor){}){}
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

    // Saves the username to DataStore
    override suspend fun saveUsername(username: String) {
        userDataStore.edit { preferences ->
            preferences[USERNAME] = username
        }
    }

    // Saves the user session token in SharedPreferences (for session management)
    override suspend fun saveUserSession(token: String) {
        preferences.edit()
            .putString("USER_TOKEN", token)
            .apply()
    }

    // Clear the user session in SharedPreferences
    override suspend fun clearSession() {
        preferences.edit()
            .remove("USER_TOKEN")
            .apply()
    }

    // Check if the user is logged in by checking for a non-null token in SharedPreferences
    override fun isUserLoggedIn(): Boolean {
        val token = preferences.getString("USER_TOKEN", null)
        return !token.isNullOrEmpty()
    }

}

