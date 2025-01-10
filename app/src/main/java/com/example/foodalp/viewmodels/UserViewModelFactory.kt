//package com.example.foodalp.viewmodels
//
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.ViewModelProvider
//import com.example.foodalp.repositories.NetworkUserRepository
//import com.example.foodalp.services.UserAPIService
//import com.example.foodalp.services.AuthenticationAPIService
//import android.content.SharedPreferences
//import androidx.datastore.core.DataStore
//import androidx.datastore.preferences.core.Preferences
//
//class UserViewModelFactory(
//    private val userDataStore: DataStore<Preferences>, // Add DataStore for user data
//    private val userAPIService: UserAPIService,
//    private val authenticationAPIService: AuthenticationAPIService,
//    private val preferences: SharedPreferences // Add SharedPreferences for session management
//) : ViewModelProvider.Factory {
//
//    override fun <T : ViewModel> create(modelClass: Class<T>): T {
//        if (modelClass.isAssignableFrom(UserViewModel::class.java)) {
//            val userRepository = NetworkUserRepository(userDataStore, userAPIService, preferences)
//            return UserViewModel() as T
//        }
//        throw IllegalArgumentException("Unknown ViewModel class")
//    }
//}
