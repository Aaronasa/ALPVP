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
import com.example.foodalp.View.LoginView
import com.example.foodalp.View.RegisterView
import com.example.foodalp.View.Welcomeview
import kotlinx.coroutines.delay

enum class ListScreen {
    Launchview,
    Welcomeview,
    Registerview,
    Loginview
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
                Welcomeview(navController)
            }
            composable(ListScreen.Registerview.name) {
                RegisterView()
            }
            composable(ListScreen.Loginview.name) {
                LoginView()
            }
        }
    }
}

@Composable
fun LaunchScreen(navController: NavHostController) {
    Launchview()

    LaunchedEffect(Unit) {
        delay(3000)
        navController.navigate(ListScreen.Welcomeview.name) {
            popUpTo(ListScreen.Welcomeview.name) { inclusive = true }
        }
    }
}


