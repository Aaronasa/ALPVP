package com.example.foodalp.View

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.SubcomposeAsyncImage
import coil.compose.rememberAsyncImagePainter
import com.example.foodalp.R
import com.example.foodalp.enums.ListScreen
import com.example.foodalp.models.RestaurantModel
import com.example.foodalp.uistates.CityState
import com.example.foodalp.uistates.RestaurantState
import com.example.foodalp.viewmodel.CityViewModel
import com.example.foodalp.viewmodel.RestaurantViewModel
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CityDetailViewAdmin(
    cityId: Int,
    token: String,
    navController: NavController,
    username: String,
    userId: Int,
    role: Int,
    restaurantViewModel: RestaurantViewModel = viewModel(),
    cityViewModel: CityViewModel = viewModel()
) {

    val Restaurant by restaurantViewModel.admin.collectAsState()
    val RestaurantUIstate by restaurantViewModel.UIstate.collectAsState()

    // State for selected image URI
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }

    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri ->
            selectedImageUri = uri
            Log.d("CityDetailViewAdmin", "Image selected: $uri")
        }
    )

    // State initialization
    var isCityDataLoaded by remember { mutableStateOf(false) }
    var cityName by remember { mutableStateOf("") }
    var cityImage by remember { mutableStateOf("") }
    var cityName1 by remember { mutableStateOf("") }
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    var isRestaurantLoaded by remember { mutableStateOf(false) }

    // Observing city state
    val cityState by cityViewModel.cityState.observeAsState(CityState.Loading)

    // LaunchedEffect for loading city details
    LaunchedEffect(cityId) {
        if (!isCityDataLoaded) {
            cityViewModel.FetchCityById(token, cityId) { city ->
                city?.let {
                    cityName = it.name
                    cityImage = it.image
                    cityName1 = it.name
                    isCityDataLoaded = true
                } ?: Log.e("CityDetailViewAdmin", "Failed to load city details")
            }
        }
        if (!isRestaurantLoaded) {
            restaurantViewModel.fetchAllRestaurantsAdmin(token)
            isRestaurantLoaded = true
        }
    }

    // Custom font family
    val customFontFamily = FontFamily(Font(R.font.jua))

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Box {
            // Background Image
            Image(
                painter = painterResource(id = R.drawable.group_8),
                contentDescription = "Background orange",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )

            // Text Overlay
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                contentAlignment = Alignment.TopStart
            ) {
                Column {
                    Text(
                        text = "Name",
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

                    SubcomposeAsyncImage(
                        model = cityImage,
                        contentDescription = "City Image",
                        loading = {
                            CircularProgressIndicator()
                        },
                        error = {
                            Text("Image failed to load")
                        },
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                            .clip(RoundedCornerShape(8.dp))
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Button(onClick = {
                        navController.navigate(
                            "CityUDViewAdmin/${cityId}/${token}/${username}/${userId}/${role}"
                        )
                    }) {
                        Text("Update and Delete City")
                    }


                    Row(modifier = Modifier.padding(top = 40.dp)) {
                        Column{
                            val role = 2
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
                                            Log.d("HomePage", "Role Id: ${role}")
                                            RestaurantGridAdmin(
                                                token = token,
                                                navController = navController,
                                                restaurants = Restaurant,
                                                username = username,
                                                role = role,
                                                userId = userId,
                                                city = cityId,
                                                restaurantViewModel = restaurantViewModel
                                            )
                                        }
                                    }
                                }

                                is RestaurantState.Start -> {
                                    Text("Welcome!")  // Initial state or any placeholder UI
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun RestaurantGridAdmin(
    token: String,
    navController: NavController,
    restaurants: List<RestaurantModel>,
    username : String,
    role: Int,
    userId : Int,
    city: Int,
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
                    onCardClick = { navController.navigate(ListScreen.RestaurantDetailViewAdmin.name + "/${restaurant.id}/${token}/${username}/${userId}/${role}/${city}") },
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


/**
 * Create a MultipartBody.Part from a given URI.
 */

fun getRealPathFromURI2(context: Context, uri: Uri): String? {
    val cursor = context.contentResolver.query(uri, null, null, null, null)
    return if (cursor != null && cursor.moveToFirst()) {
        val index = cursor.getColumnIndex("_data")
        val path = if (index != -1) cursor.getString(index) else null
        cursor.close()
        path
    } else {
        null
    }
}


