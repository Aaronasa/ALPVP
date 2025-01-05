package com.example.foodalp.View

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.foodalp.R
import com.example.foodalp.uiStates.UserStatusUIState
import com.example.foodalp.uiStates.UserUIState
import com.example.foodalp.viewmodels.UserViewModel
import android.content.Context
import android.content.SharedPreferences
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import com.example.foodalp.AppContainer
import com.example.foodalp.Route.ListScreen
import com.example.foodalp.models.RestaurantModel
import com.example.foodalp.viewmodel.RestaurantViewModel

@Composable
fun HomePage(
    navController: NavController,
    userViewModel: UserViewModel = viewModel(),
    restaurantViewModel: RestaurantViewModel = viewModel()
) {
    val context = LocalContext.current  // Get context here
//    val restaurantViewModel = RestaurantViewModel()
    val uiState by restaurantViewModel.uiState.collectAsState()

    // Assume that you retrieve the token and email from somewhere (e.g., shared preferences or session storage)
    val token = getUserToken()  // Replace this with your logic to get the token
    val email = getUserEmail()  // Replace this with your logic to get the email

    // Check if the token or email is null/empty and handle accordingly
    if (!token.isNullOrEmpty() && !email.isNullOrEmpty()) {
        LaunchedEffect(true) {
            userViewModel.loadUserData(token, email)
        }
    } else {
        // Handle scenario where user is not logged in
        // Maybe navigate to login page or show error
        Log.e("HomePage", "User not logged in. Token or email is missing.")
    }

    // Observe the user state from the viewModel
    val userState = userViewModel.userState.observeAsState(UserUIState.Loading)
    Log.d("HomePage", "Current state: ${userState.value}")

    val restaurantState = restaurantViewModel.uiState.collectAsState()

    LaunchedEffect(true) {
        if (restaurantState.value.restaurants.isEmpty()) {
            restaurantViewModel.fetchAllRestaurants()  // Load restaurants if not yet fetched
        }
    }

    when (val state = userState.value) {
        is UserUIState.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
        is UserUIState.Success -> {
            val user = state.user
            user?.let {
                Column(modifier = Modifier
                    .fillMaxSize()
                    .background(color = Color.White)) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(color = Color.White)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.group_8),
                            contentDescription = "Background orange",
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )

                        Column(
                            modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.Top,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            // HeaderSection is defined here
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 20.dp, vertical = 20.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column {
                                    Text(
                                        text = "Hi ${it.username}",
                                        fontSize = 24.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color(0xFF1B1C21)
                                    )
                                    Text(
                                        text = "Explore Indonesia!",
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.Normal,
                                        color = Color(0xFF1B1C21)
                                    )
                                }

                                Box(
                                    modifier = Modifier
                                        .size(80.dp)
                                        .clip(CircleShape)
                                        .background(Color(0xFF991E3D))
                                        .padding(8.dp)
                                ) {
                                    Image(
                                        painter = painterResource(id = R.drawable.ellipse_11),
                                        contentDescription = "Food Icon",
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .clip(CircleShape)
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(20.dp))
//                            when {
//                                restaurantState.value.isLoading -> {
//                                    CircularProgressIndicator()  // Show loading while fetching restaurants
//                                }
//                                restaurantState.value.restaurants.isEmpty() -> {
//                                    Text("No restaurants available.")
//                                }
//                                else -> {
//                                    val restaurants = restaurantState.value.restaurants
//                                    RestaurantGrid(
//                                        restaurants = restaurants,  // Pass the fetched restaurant data
//                                        restaurantViewModel = restaurantViewModel
//                                    )
//                                }
//                            }


                            Spacer(modifier = Modifier.height(30.dp))  // Space between elements
                            Button (
                                onClick = { navController.navigate(ListScreen.AddRestaurantView.name) },
                                modifier = Modifier
                                    .fillMaxWidth(0.6f)
                                    .padding(horizontal = 20.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF9C254D))
                            ) {
                                Text("Add Restaurant", color = Color.White, fontSize = 16.sp)
                            }
                        }
                    }
                }
            }
        }
        is UserUIState.Error -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Error: ${state.message}",
                    color = Color.Red,
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
        UserUIState.Idle -> {
            // Handle idle state if necessary
        }
    }
}


// Retrieve token from SharedPreferences
@Composable
fun getUserToken(): String? {
    val context = LocalContext.current
    val sharedPreferences = context.getSharedPreferences("user_session", Context.MODE_PRIVATE)
    return sharedPreferences.getString("token", null)
}

// Retrieve email from SharedPreferences
@Composable
fun getUserEmail(): String? {
    val context = LocalContext.current
    val sharedPreferences = context.getSharedPreferences("user_session", Context.MODE_PRIVATE)
    return sharedPreferences.getString("email", null)
}

@Composable
fun SearchBar() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
            .height(56.dp)
            .background(
                color = Color.White,
                shape = RoundedCornerShape(12.dp)
            ),
        contentAlignment = Alignment.CenterStart
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.baseline_search_24),
                contentDescription = "Search Icon",
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = "Where do you want to go?",
                fontSize = 14.sp,
                fontWeight = FontWeight.Normal,
                color = Color(0xFFB3B3B3)
            )
        }
    }
}

@Composable
fun RestaurantGrid(
    restaurants: List<RestaurantModel>,
    restaurantViewModel: RestaurantViewModel,
    modifier: Modifier = Modifier
) {
    // Group restaurants into pairs (two restaurants per row)
    val restaurantPairs = restaurants.chunked(2)

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        items(restaurantPairs) { pair ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                pair.forEach { restaurant ->
                    Log.d("Restaurant Grid", "Restaurant response: $restaurant")
                    RestaurantCard(
                        restaurantId = restaurant.id,
                        viewModel = restaurantViewModel,
                        modifier = Modifier
                            .weight(1f)
                            .padding(8.dp)
                    )
                }

                // Add a spacer if the row has fewer than 2 items
                if (pair.size < 2) {
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
        }
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewHomepage() {
    HomePage(navController = rememberNavController())
}
