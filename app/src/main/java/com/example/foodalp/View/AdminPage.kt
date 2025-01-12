package com.example.foodalp.View

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
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
import com.example.foodalp.uistates.UserUIState
import com.example.foodalp.viewmodels.UserViewModel
import android.content.Context
import androidx.compose.foundation.clickable
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.foodalp.models.CityModel
import com.example.foodalp.uistates.CityState
import com.example.foodalp.viewmodel.CityViewModel

@Composable
fun AdminPage(
    navController: NavController,
    userViewModel: UserViewModel = viewModel(),
    cityViewModel: CityViewModel = viewModel()
) {
    val context = LocalContext.current  // Get context here



    // Assume that you retrieve the token and email from somewhere (e.g., shared preferences or session storage)
    val token = getUserTokena()  // Replace this with your logic to get the token
    val email = getUserEmaila()  // Replace this with your logic to get the email
    val City by cityViewModel.City.collectAsState()

    // Check if the token or email is null/empty and handle accordingly
    if (!token.isNullOrEmpty() && !email.isNullOrEmpty()) {
        LaunchedEffect(true) {
            userViewModel.loadUserData(token, email)
        }
    } else {
        Log.e("AdminPage", "User not logged in. Token or email is missing.")
    }


    // Observe the user state from the viewModel
    val userState = userViewModel.userState.observeAsState(UserUIState.Loading)
    Log.d("AdminPage", "Current state: ${userState.value}")

    LaunchedEffect(Unit) {
        if (token != null) {
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
                                        text = "Wellcome To Admin Page",
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


                            Button(
                                onClick = {
                                    navController.navigate("AddCityView")
                                },
                                modifier = Modifier
                                    .fillMaxWidth(0.6f),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color(
                                        0xFF9C254D
                                    )
                                )
                            ) {
                                Text(
                                    "Add City",
                                    color = Color.White,
                                    fontFamily = customFontFamily,
                                    fontSize = 20.sp
                                )
                            }
                            CityGrid1(
                                token = token.toString(),
                                navController = navController,
                                cities = City,
                                cityViewModel = cityViewModel,
                                username = it.username,
                                role = it.roleId,
                                userId = it.id
                            )
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
fun getUserTokena(): String? {
    val context = LocalContext.current
    val sharedPreferences = context.getSharedPreferences("user_session", Context.MODE_PRIVATE)
    return sharedPreferences.getString("token", null)
}

@Composable
fun getUserEmaila(): String? {
    val context = LocalContext.current
    val sharedPreferences = context.getSharedPreferences("user_session", Context.MODE_PRIVATE)
    return sharedPreferences.getString("email", null)
}

@Composable
fun CityGrid1(
    token: String,
    navController: NavController,
    cities: List<CityModel>,
    cityViewModel: CityViewModel,
    username: String,
    role: Int,
    userId: Int,
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
            CityCard1(
                navController = navController,
                city = city,
                viewModel = cityViewModel,
                username = username,
                role = role,
                userId = userId,
                modifier = Modifier
                    .padding(8.dp)
            )
        }
    }
}



@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewAdminPage() {
    AdminPage(navController = rememberNavController())
}