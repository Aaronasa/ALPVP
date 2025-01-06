package com.example.foodalp.Route

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.foodalp.View.AddRestaurantView
import com.example.foodalp.View.AppDescView
import com.example.foodalp.View.DetailProfileView
import com.example.foodalp.View.Launchview
import com.example.foodalp.View.LoginView
import com.example.foodalp.View.HomePage
import com.example.foodalp.View.MenujuHomepage1View
import com.example.foodalp.View.MenujuHomepage2View
import com.example.foodalp.View.MenujuHomepage3View
import com.example.foodalp.View.RegisterView
import com.example.foodalp.View.UpdateProfileView
import com.example.foodalp.View.Welcomeview
import com.example.foodalp.viewmodel.RestaurantViewModel
import com.example.foodalp.viewmodels.UserViewModel
import kotlinx.coroutines.delay

enum class ListScreen {
    Launchview,
    Welcomeview,
    Registerview,
    Loginview,
    MenujuHomepage1View,
    MenujuHomepage2View,
    MenujuHomepage3View,
    AppDescView,
    HomePage,
    AddRestaurantView,
    UpdateProfileView,
    DetailProfileView
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
                RegisterView(navController)
            }
            composable(ListScreen.Loginview.name) {
                LoginView(navController)
            }
            composable(ListScreen.MenujuHomepage1View.name) {
                MenujuHomepage1View(navController)
            }
            composable(ListScreen.MenujuHomepage2View.name) {
                MenujuHomepage2View(navController)
            }
            composable(ListScreen.MenujuHomepage3View.name) {
                MenujuHomepage3View(navController)
            }
            composable(ListScreen.AppDescView.name) {
                AppDescView(navController)
            }
            composable(ListScreen.HomePage.name) {
                val userViewModel = viewModel<UserViewModel>()
                HomePage(navController, userViewModel)
            }
            composable(ListScreen.AddRestaurantView.name) {
                AddRestaurantView(navController)
                composable(ListScreen.UpdateProfileView.name) {
                    UpdateProfileView(navController)
                }
                composable(ListScreen.DetailProfileView.name) {
                    DetailProfileView(navController)
                }

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
