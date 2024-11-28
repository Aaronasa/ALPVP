package com.example.foodalp.Route

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.foodalp.View.Launchview
import com.example.foodalp.View.Welcomeview
import kotlinx.coroutines.delay

enum class ListScreen {
    Launchview, // Initial screen
    MainScreen  // Replace with your next screen
}

@Composable
fun AppRouting() {
    val navController = rememberNavController()

    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = ListScreen.Launchview.name,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(ListScreen.Launchview.name) {
                LaunchScreen(navController)
            }
            composable(ListScreen.MainScreen.name) {
                Welcomeview()
            }
        }
    }
}

@Composable
fun LaunchScreen(navController: NavHostController) {
    Launchview()

    LaunchedEffect(Unit) {
        delay(3000)
        navController.navigate(ListScreen.MainScreen.name) {
            popUpTo(ListScreen.Launchview.name) { inclusive = true } // Remove Launchview from the back stack
        }
    }
}
