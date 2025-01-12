package com.example.foodalp.View

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.foodalp.R
import com.example.foodalp.View.FoodCard
import com.example.foodalp.View.RestaurantCard
import com.example.foodalp.View.getUserToken
import com.example.foodalp.enums.ListScreen
import com.example.foodalp.models.FoodModel
import com.example.foodalp.models.RestaurantModel
import com.example.foodalp.uistates.CityState
import com.example.foodalp.uistates.FoodState
import com.example.foodalp.uistates.RestaurantState
import com.example.foodalp.viewmodel.CityViewModel
import com.example.foodalp.viewmodel.FoodViewModel
import com.example.foodalp.viewmodel.RestaurantViewModel


@Composable
fun CityDetailView(
    cityId: Int,
    token: String,
    navController: NavController,
    username: String,
    userId: Int,
    role: Int,
    foodViewModel: FoodViewModel = viewModel(),
    restaurantViewModel: RestaurantViewModel = viewModel(),
    cityViewModel: CityViewModel = viewModel()
) {
    val token1 = getUserToken()

    val foods by foodViewModel.foodList.collectAsState()
    val foodUIState by foodViewModel.uiState.collectAsState()

    val restaurants by restaurantViewModel.Restaurant.collectAsState()
    val restaurantUIState by restaurantViewModel.UIstate.collectAsState()

    var isCityDataLoaded by remember { mutableStateOf(false) }
    var isFoodLoaded by remember { mutableStateOf(false) }
    var isRestaurantLoaded by remember { mutableStateOf(false) }
    var cityName by remember { mutableStateOf("") }
    var cityImage by remember { mutableStateOf("") }

    val cityState = cityViewModel.cityState.observeAsState(CityState.Loading)

    LaunchedEffect(cityId) {
        Log.d("CityDetailView", "Loading city data for ID: $cityId")
        if (!isCityDataLoaded) {
            cityViewModel.FetchCityById(
                token = token1.toString(),
                cityId = cityId
            ) { city ->
                city?.let {
                    cityName = it.name
                    cityImage = it.image
                    isCityDataLoaded = true
                } ?: Log.e("CityDetailView", "Failed to load city details")
            }
        }

        if (!isFoodLoaded) {
            foodViewModel.fetchAllFoods(token)
            isFoodLoaded = true
        }

        if (!isRestaurantLoaded) {
            restaurantViewModel.fetchAllRestaurants(token)
            isRestaurantLoaded = true
        }
    }

    val customFontFamily = FontFamily(Font(R.font.jua))

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Box {
            Image(
                painter = painterResource(id = R.drawable.group_8),
                contentDescription = "Background orange",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                contentAlignment = Alignment.TopStart // Semua elemen akan disusun dari bagian atas
            ) {
                Row {
                    Column {
                        Text(
                            text = "Location",
                            color = Color.Gray,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = customFontFamily,
                            modifier = Modifier.padding(start = 20.dp, top = 20.dp)
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = cityName,
                            color = Color(0xFF0C094E),
                            fontSize = 30.sp,
                            fontFamily = customFontFamily,
                            fontWeight = FontWeight.Normal,
                            modifier = Modifier.padding(start = 15.dp)
                        )
                    }
                }
                Row(modifier = Modifier.padding(top = 80.dp)) {
                    Column {
                        Button(
                            onClick = { navController.navigate(ListScreen.AddFoodView.name + "/${role}") },
                            modifier = Modifier
                                .fillMaxWidth() // Perluas lebar untuk menguji
                                .padding(horizontal = 20.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFF9C254D)
                            )
                        ) {
                            Text("Add Food", color = Color.White, fontSize = 16.sp)
                        }

                        Button(
                            onClick = { navController.navigate(ListScreen.AddRestaurantView.name + "/${role}") },
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

                        when (foodUIState) {
                            is FoodState.Loading -> {
                                CircularProgressIndicator()
                            }

                            is FoodState.Failed -> {
                                val errorMessage =
                                    (foodUIState as FoodState.Failed).errorMessage
                                Text(errorMessage)
                            }

                            is FoodState.Success -> {
                                val foods =
                                    (foodUIState as FoodState.Success).data
                                if (foods.isEmpty()) {
                                    Text("No foods available.")
                                } else {
                                    if (token != null) {
                                        Log.d("HomePage", "Role Id: ${role}")
                                        FoodGrid(
                                            token = token,
                                            navController = navController,
                                            foods = foods,
                                            username = username,
                                            role = role,
                                            userId = userId,
                                            foodViewModel = foodViewModel
                                        )
                                    }
                                }
                            }

                            is FoodState.Start -> {
                                Text("Welcome!")
                            }
                        }

                        when (restaurantUIState) {
                            is RestaurantState.Loading -> {
                                CircularProgressIndicator()
                            }

                            is RestaurantState.Failed -> {
                                val errorMessage =
                                    (restaurantUIState as RestaurantState.Failed).errorMessage
                                Text(errorMessage)
                            }

                            is RestaurantState.Success -> {
                                val restaurants =
                                    (restaurantUIState as RestaurantState.Success).data
                                if (restaurants.isEmpty()) {
                                    Text("No restaurants available.")
                                } else {
                                    if (token != null) {
                                        Log.d("HomePage", "Role Id: ${role}")
                                        RestaurantGrid(
                                            token = token,
                                            navController = navController,
                                            restaurants = restaurants,
                                            username = username,
                                            role = role,
                                            userId = userId,
                                            restaurantViewModel = restaurantViewModel
                                        )
                                    }
                                }
                            }

                            is RestaurantState.Start -> {
                                Text("Welcome!")
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun FoodGrid(
    token: String,
    navController: NavController,
    foods: List<FoodModel>,
    username: String,
    role: Int,
    userId: Int,
    foodViewModel: FoodViewModel,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        items(foods) { food ->
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                FoodCard(
                    foodName = food.name,
                    foodImageRes = R.drawable.ic_launcher_background, // Replace with food.image if dynamic image is used
                    onCardClick = {
                        navController.navigate(
                            ListScreen.FoodDetailView.name + "/${food.id}/${token}/${username}/${userId}/${role}"
                        )
                    },
                    modifier = Modifier
                        .weight(1f)
                        .padding(8.dp)
                )
            }
        }
    }
}

@Composable
fun RestaurantGrid(
    token: String,
    navController: NavController,
    restaurants: List<RestaurantModel>,
    username : String,
    role: Int,
    userId : Int,
    restaurantViewModel: RestaurantViewModel,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        items(restaurants) { restaurant ->
            Row(
                modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween
            ) {
                RestaurantCard(
                    onCardClick = { navController.navigate(ListScreen.RestaurantDetailView.name + "/${restaurant.id}/${token}/${username}/${userId}/${role}")},
//                    onCardClick = { navController.navigate(ListScreen.UpdateRestaurantView.name + "/${restaurant.id}/${token}") },
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
