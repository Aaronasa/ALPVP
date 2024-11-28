package com.example.foodalp.Route

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
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
    Welcomeview, // New screen after Launchview
    MainScreen  // Final main screen
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
            composable(ListScreen.Welcomeview.name) {
                Welcomeview()
            }
        }
    }
}

@Composable
fun LaunchScreen(navController: NavHostController) {
    Launchview()

    LaunchedEffect(Unit) {
        delay(3000) // Wait for 3 seconds
        navController.navigate(ListScreen.Welcomeview.name) {
            popUpTo(ListScreen.Welcomeview.name) { inclusive = true } // Remove Launchview from the back stack
        }
    }
}

//@Composable
//fun WelcomeScreen(navController: NavHostController) {
//        navController.navigate(ListScreen.MainScreen.name)
//}

