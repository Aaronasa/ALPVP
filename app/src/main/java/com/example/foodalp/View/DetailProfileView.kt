package com.example.foodalp.View

import android.content.Context
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.foodalp.R
import com.example.foodalp.uistates.UserUIState
import com.example.foodalp.viewmodels.UserViewModel

@Composable
fun DetailProfileView(
    navController: androidx.navigation.NavHostController,
    userViewModel: UserViewModel = viewModel()
) {
    val customFontFamily = FontFamily(
        Font(R.font.jua)
    )

    val context = LocalContext.current

    // Assume that you retrieve the token and email from somewhere (e.g., shared preferences or session storage)
    val token = getUserToken1()  // Replace this with your logic to get the token
    val email = getUserEmail1()  // Replace this with your logic to get the email

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
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.group_8),
                        contentDescription = "Background orange",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                    Column(
                        modifier = Modifier
                            .fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        // Top Background Shape
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(120.dp)
                                .background(
                                    Color(0xFF991E3D),
                                    shape = RoundedCornerShape(
                                        bottomEnd = 16.dp,
                                        bottomStart = 16.dp
                                    )
                                ),
                            contentAlignment = Alignment.TopCenter
                        ) {}

                        // Profile Picture
                        Spacer(modifier = Modifier.height(-50.dp)) // Adjusted to place half the profile picture into the header
                        Box(
                            modifier = Modifier
                                .size(150.dp) // Increased size of the profile picture
                                .background(Color.White, shape = CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.ellipse_11), // Replace with your image resource
                                contentDescription = "Profile Picture",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .size(140.dp) // Adjusted size for the internal image
                                    .clip(CircleShape)
                            )
                        }


                        // Name and Location
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            " ${it.username}",
                            fontWeight = FontWeight.Bold,
                            fontSize = 30.sp,
                            fontFamily = customFontFamily
                        )
                        Text(
                            "Surabaya, ID",
                            color = Color.Gray,
                            fontSize = 20.sp,
                            fontFamily = customFontFamily
                        )

                        // Email and Password
                        Spacer(modifier = Modifier.height(24.dp))
                        Column(
                            horizontalAlignment = Alignment.Start,
                            modifier = Modifier
                                .padding(horizontal = 32.dp)
                                .fillMaxWidth()
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    "Email",
                                    fontWeight = FontWeight.Bold,
                                    color = Color.Gray,
                                    fontSize = 20.sp,
                                    fontFamily = customFontFamily
                                )
                                Text(
                                    " ${it.email}",
                                    fontSize = 20.sp,
                                    fontFamily = customFontFamily
                                )
                            }
                        }

                        // Edit and Sign Out Buttons
                        Spacer(modifier = Modifier.height(100.dp))
                        Button(
                            onClick = { navController.navigate("UpdateProfileView") },
                            modifier = Modifier
                                .fillMaxWidth(0.6f),
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF9C254D))
                        ) {
                            Text(
                                "Edit",
                                color = Color.White,
                                fontFamily = customFontFamily,
                                fontSize = 20.sp
                            )
                        }

                        Spacer(modifier = Modifier.height(16.dp))
                        Button(
                            onClick = {
                                val tokens = token
                                if (!tokens.isNullOrEmpty()) {
                                    userViewModel.logout(
                                        token = tokens,
                                        onSuccess = {
                                            // Navigasi ke layar login setelah logout berhasil
                                            navController.navigate("LoginView") {
                                                popUpTo(navController.graph.startDestinationId)
                                            }
                                        },
                                        onError = { error ->
                                            // Tampilkan pesan kesalahan
                                            Log.e("Logout", error)
                                        }
                                    )
                                } else {
                                    Log.e("Logout", "Token not found.")
                                }
                            },
                            modifier = Modifier
                                .fillMaxWidth(0.6f),
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF9C254D))
                        ) {
                            Text(
                                "Sign Out",
                                color = Color.White,
                                fontFamily = customFontFamily,
                                fontSize = 20.sp
                            )
                        }

                        Spacer(modifier = Modifier.height(40.dp))

                        Button(
                            onClick = {
                                val tokens = token
                                if (!tokens.isNullOrEmpty()) {
                                    userViewModel.delete(
                                        token = tokens,
                                        onSuccess = {
                                            // Navigasi ke layar login setelah logout berhasil
                                            navController.navigate("LoginView") {
                                                popUpTo(navController.graph.startDestinationId)
                                            }
                                        },
                                        onError = { error ->
                                            // Tampilkan pesan kesalahan
                                            Log.e("Delete", error)
                                        }
                                    )
                                } else {
                                    Log.e("Delete", "Token not found.")
                                }
                            },
                            modifier = Modifier
                                .fillMaxWidth(0.6f),
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF9C254D))
                        ) {
                            Text(
                                "Delete Account",
                                color = Color.White,
                                fontFamily = customFontFamily,
                                fontSize = 20.sp
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
fun getUserToken1(): String? {
    val context = LocalContext.current
    val sharedPreferences = context.getSharedPreferences("user_session", Context.MODE_PRIVATE)
    return sharedPreferences.getString("token", null)
}

@Composable
fun getUserEmail1(): String? {
    val context = LocalContext.current
    val sharedPreferences = context.getSharedPreferences("user_session", Context.MODE_PRIVATE)
    return sharedPreferences.getString("email", null)
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DetailProfilePreview() {
    DetailProfileView(navController = rememberNavController())
}
