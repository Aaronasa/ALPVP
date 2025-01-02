package com.example.foodalp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.ExperimentalMaterial3Api
import com.example.foodalp.Route.AppRouting
import com.example.foodalp.ui.theme.FoodALPTheme
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.example.foodalp.AppContainer.dataStore

class MainActivity : ComponentActivity() {

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Access the dataStore from the AppContainer
        val userDataStore: DataStore<Preferences> = (applicationContext as FoodApplication).applicationContext.dataStore

        setContent {
            FoodALPTheme {
                AppRouting()
            }
        }
    }
}
