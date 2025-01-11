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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.foodalp.R
import com.example.foodalp.uistates.UserUIState
import com.example.foodalp.viewmodels.UserViewModel
import android.content.Context
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.foundation.clickable
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.ui.platform.LocalContext
import com.example.foodalp.enums.ListScreen
import com.example.foodalp.models.CityModel
import com.example.foodalp.models.RestaurantModel
import com.example.foodalp.uistates.CityState
import com.example.foodalp.uistates.RestaurantState
import com.example.foodalp.viewmodel.CityViewModel
import com.example.foodalp.viewmodel.RestaurantViewModel

@Composable
fun HomePage(
    navController: NavController,
    userViewModel: UserViewModel = viewModel(),
    restaurantViewModel: RestaurantViewModel = viewModel(),
    cityViewModel: CityViewModel = viewModel()
) {
//    for restaurant
    val Restaurant by restaurantViewModel.Restaurant.collectAsState()
    val City by cityViewModel.City.collectAsState()
    val RestaurantUIstate by restaurantViewModel.UIstate.collectAsState()
    val CityUIstate by cityViewModel.UIstate.collectAsState()

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
        Log.e("HomePage", "User not logged in. Token or email is missing.")
    }

    // Observe the user state from the viewModel
    val userState = userViewModel.userState.observeAsState(UserUIState.Loading)
    Log.d("HomePage", "Current state: ${userState.value}")


    LaunchedEffect(Unit) {
        if (token != null) {
            restaurantViewModel.fetchAllRestaurants(token)
            cityViewModel.fetchAllCities(token)
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
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(color = Color.White)
                ) {
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
                                        .clickable() {
                                            // Navigasi ke UpdateUserView
                                            navController.navigate("DetailProfileView")
                                        }

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
                            when (CityUIstate) {
                                is CityState.Loading -> {
                                    CircularProgressIndicator()  // Show loading while fetching restaurants
                                }

                                is CityState.Failed -> {
                                    val errorMessage =
                                        (CityUIstate as CityState.Failed).errorMessage
                                    Text(errorMessage)  // Display the error message
                                }

                                is CityState.Success -> {
                                    val cities =
                                        (CityUIstate as CityState.Success).data
                                    if (cities.isEmpty()) {
                                        Text("No cities available.")
                                    } else {
                                        if (token != null) {
                                            CityGrid(
                                                token = token,
                                                navController = navController,
                                                cities = City,  // Pass the fetched restaurant data
                                                cityViewModel = cityViewModel
                                            )
                                        }
                                    }
                                }

                                is CityState.Start -> {
                                    Text("Welcome!")  // Initial state or any placeholder UI
                                }
                            }

                            Button(
                                onClick = { navController.navigate(ListScreen.AddRestaurantView.name) },
                                modifier = Modifier
                                    .fillMaxWidth(0.6f)
                                    .padding(horizontal = 20.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color(
                                        0xFF9C254D
                                    )
                                )
                            ) {
                                Text("Add Restaurant", color = Color.White, fontSize = 16.sp)
                            }
                            when (RestaurantUIstate) {
                                is RestaurantState.Loading -> {
                                    CircularProgressIndicator()  // Show loading while fetching restaurants
                                }

                                is RestaurantState.Failed -> {
                                    val errorMessage =
                                        (RestaurantUIstate as RestaurantState.Failed).errorMessage
                                    Text(errorMessage)  // Display the error message
                                }

                                is RestaurantState.Success -> {
                                    val restaurants =
                                        (RestaurantUIstate as RestaurantState.Success).data
                                    if (restaurants.isEmpty()) {
                                        Text("No restaurants available.")
                                    } else {
                                        if (token != null) {
                                            RestaurantGrid(
                                                token = token,
                                                navController = navController,
                                                restaurants = Restaurant,  // Pass the fetched restaurant data
                                                restaurantViewModel = restaurantViewModel
                                            )
                                        }
                                    }
                                }

                                is RestaurantState.Start -> {
                                    Text("Welcome!")  // Initial state or any placeholder UI
                                }
                            }
                            when (CityUIstate) {
                                is CityState.Loading -> {
                                    CircularProgressIndicator()  // Show loading while fetching restaurants
                                }

                                is CityState.Failed -> {
                                    val errorMessage =
                                        (CityUIstate as CityState.Failed).errorMessage
                                    Text(errorMessage)  // Display the error message
                                }

                                is CityState.Success -> {
                                    val cities =
                                        (CityUIstate as CityState.Success).data
                                    if (cities.isEmpty()) {
                                        Text("No cities available.")
                                    } else {
                                        if (token != null) {
                                            CityGrid(
                                                token = token,
                                                navController = navController,
                                                cities = City,  // Pass the fetched restaurant data
                                                cityViewModel = cityViewModel
                                            )
                                        }
                                    }
                                }

                                is CityState.Start -> {
                                    Text("Welcome!")  // Initial state or any placeholder UI
                                }
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

@Composable
fun RestaurantGrid(
    token: String,
    navController: NavController,
    restaurants: List<RestaurantModel>,
    restaurantViewModel: RestaurantViewModel,
    modifier: Modifier = Modifier
) {
    // Group restaurants into pairs (two restaurants per row)
    val restaurantPairs = restaurants.chunked(1)

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        items(restaurants) { restaurant ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                RestaurantCard(
                    onCardClick = { navController?.navigate(ListScreen.UpdateRestaurantView.name + "/${restaurant.id}/${token}") },
                    navController = navController,
                    restaurant = restaurant,
                    viewModel = restaurantViewModel,
                    modifier = Modifier
                        .weight(1f)
                        .padding(8.dp)
                )
            }
        }
    }
}

@Composable
fun CityGrid(
    token: String,
    navController: NavController,
    cities: List<CityModel>,
    cityViewModel: CityViewModel,
    modifier: Modifier = Modifier
) {
    LazyRow (
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp) // Spacing between items
    ) {
        items(cities) { city ->
            Log.d("CityGrid1", "Cities: ${cities.size} - $cities")
            CityCard(
                navController = navController,
                city = city,
                viewModel = cityViewModel,
                modifier = Modifier
                    .padding(8.dp)
            )
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

@Composable
fun getUserEmail(): String? {
    val context = LocalContext.current
    val sharedPreferences = context.getSharedPreferences("user_session", Context.MODE_PRIVATE)
    return sharedPreferences.getString("email", null)
}


//@Preview(showBackground = true, showSystemUi = true)
//@Composable
//fun PreviewHomepage() {
//    HomePage(navController = rememberNavController())
//}
