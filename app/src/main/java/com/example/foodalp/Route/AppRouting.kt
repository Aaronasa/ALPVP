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
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.foodalp.View.AddCityView
import com.example.foodalp.View.AddRestaurantView
import com.example.foodalp.View.AddReview
import com.example.foodalp.View.AdminPage
import com.example.foodalp.View.AppDescView
import com.example.foodalp.View.DetailProfileView
import com.example.foodalp.View.Launchview
import com.example.foodalp.View.LoginView
import com.example.foodalp.View.HomePage
import com.example.foodalp.View.CityDetailView
import com.example.foodalp.View.CityDetailViewAdmin
import com.example.foodalp.View.MenujuHomepage1View
import com.example.foodalp.View.MenujuHomepage2View
import com.example.foodalp.View.MenujuHomepage3View
import com.example.foodalp.View.RegisterView
import com.example.foodalp.View.RestaurantDetailView
import com.example.foodalp.View.UpdateProfileView
import com.example.foodalp.View.UpdateRestaurantView
import com.example.foodalp.View.UpdateReview
import com.example.foodalp.View.Welcomeview
import com.example.foodalp.models.RestaurantModel
import com.example.foodalp.viewmodel.RestaurantViewModel
import com.example.foodalp.enums.ListScreen
import com.example.foodalp.viewmodels.UserViewModel
import com.google.gson.Gson
import kotlinx.coroutines.delay


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
            composable(
                route = ListScreen.AddRestaurantView.name + "/{role}",
                arguments = listOf(
                    navArgument("role") { type = NavType.IntType }
                )
            ) { backStackEntry ->
                val role = backStackEntry.arguments?.getInt("role")
                if (role != null) {
                    AddRestaurantView(navController, role)
                }
            }
            composable(ListScreen.UpdateProfileView.name) {
                UpdateProfileView(navController)
            }
            composable(ListScreen.DetailProfileView.name) {
                DetailProfileView(navController)
            }
            composable(ListScreen.AdminPage.name) {
                AdminPage(navController)
            }
            composable(ListScreen.AddCityView.name) {
                AddCityView(navController)
            }
            composable(
                route = ListScreen.UpdateRestaurantView.name + "/{id}/{token}",
                arguments = listOf(
                    navArgument("id") { type = NavType.IntType },
                    navArgument("token") { type = NavType.StringType },
                )
            ) { backStackEntry ->
                val restaurantId = backStackEntry.arguments?.getInt("id")
                val token = backStackEntry.arguments?.getString("token")

                if (restaurantId != null && token != null) {
                    UpdateRestaurantView(navController, restaurantId, token)
                }
            }
            composable(
                route = ListScreen.CityDetailView.name + "/{id}/{token}/{username}/{userId}/{role}",
                arguments = listOf(
                    navArgument("id") { type = NavType.IntType },
                    navArgument("token") { type = NavType.StringType },
                    navArgument("username") { type = NavType.StringType },
                    navArgument("userId") { type = NavType.IntType },
                    navArgument("role") { type = NavType.IntType }
                )
            ) { backStackEntry ->
                val cityId = backStackEntry.arguments?.getInt("id")
                val token = backStackEntry.arguments?.getString("token")
                val username = backStackEntry.arguments?.getString("username")
                val userId = backStackEntry.arguments?.getInt("userId")
                val role = backStackEntry.arguments?.getInt("role")
                if (cityId != null && token != null && username != null && userId != null && role != null) {
                    CityDetailView(cityId, token, navController, username, userId, role)
                }
            }
            composable(
                route = ListScreen.CityDetailViewAdmin.name + "/{id}/{token}/{username}/{userId}/{role}",
                arguments = listOf(
                    navArgument("id") { type = NavType.IntType },
                    navArgument("token") { type = NavType.StringType },
                    navArgument("username") { type = NavType.StringType },
                    navArgument("userId") { type = NavType.IntType },
                    navArgument("role") { type = NavType.IntType }
                )
            ) { backStackEntry ->
                // Retrieve the arguments from the backStackEntry
                val cityId = backStackEntry.arguments?.getInt("id")
                val token = backStackEntry.arguments?.getString("token")
                val username = backStackEntry.arguments?.getString("username")
                val userId = backStackEntry.arguments?.getInt("userId")
                val role = backStackEntry.arguments?.getInt("role")
                if (cityId != null && token != null && username != null && userId != null && role != null) {
                    CityDetailViewAdmin(cityId, token, navController, username, userId, role)
                }
            }
            composable(
                route = ListScreen.AddReview.name + "/{id}/{token}/{username}/{userId}/{role}",
                arguments = listOf(
                    navArgument("id") { type = NavType.IntType },
                    navArgument("token") { type = NavType.StringType },
                    navArgument("username") { type = NavType.StringType },
                    navArgument("userId") { type = NavType.IntType },
                    navArgument("role") { type = NavType.IntType }
                )

            ) { backStackEntry ->
                val restaurantId = backStackEntry.arguments?.getInt("id")
                val token = backStackEntry.arguments?.getString("token")
                val username = backStackEntry.arguments?.getString("username")
                val userId = backStackEntry.arguments?.getInt("userId")
                val role = backStackEntry.arguments?.getInt("role")
                if (restaurantId != null && token != null && username != null && userId != null && role != null) {
                    AddReview(navController, restaurantId, token, username, userId, role)
                }
            }
            composable(
                route = ListScreen.RestaurantDetailView.name + "/{id}/{token}/{username}/{userId}/{role}",
                arguments = listOf(
                    navArgument("id") { type = NavType.IntType },
                    navArgument("token") { type = NavType.StringType },
                    navArgument("username") { type = NavType.StringType },
                    navArgument("userId") { type = NavType.IntType },
                    navArgument("role") { type = NavType.IntType }
                )
            ) { backStackEntry ->
                val restaurantId = backStackEntry.arguments?.getInt("id")
                val token = backStackEntry.arguments?.getString("token")
                val username = backStackEntry.arguments?.getString("username")
                val userId = backStackEntry.arguments?.getInt("userId")
                val role = backStackEntry.arguments?.getInt("role")
                if (restaurantId != null && token != null && username != null && userId != null && role != null) {
                    RestaurantDetailView(
                        navController,
                        restaurantId,
                        token,
                        username,
                        userId,
                        role
                    )
                }
            }
            composable(
                route = ListScreen.UpdateReviewView.name + "/{id}/{token}/{username}/{role}",
                arguments = listOf(
                    navArgument("id") { type = NavType.IntType },
                    navArgument("token") { type = NavType.StringType },
                    navArgument("username") { type = NavType.StringType },
                    navArgument("role") { type = NavType.IntType }
                )
            ) {
                val reviewId = it.arguments?.getInt("id")
                val token = it.arguments?.getString("token")
                val username = it.arguments?.getString("username")
                val role = it.arguments?.getInt("role")
                if (reviewId != null && token != null && username != null && role != null) {
                    UpdateReview(navController, reviewId, token, username, role)
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

