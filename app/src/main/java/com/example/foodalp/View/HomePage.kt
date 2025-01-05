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
import androidx.compose.foundation.clickable
import androidx.compose.ui.platform.LocalContext

@Composable
fun HomePage(
    navController: NavController,
    userViewModel: UserViewModel = viewModel()
) {
    val context = LocalContext.current  // Get context here

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


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewHomepage() {
    HomePage(navController = rememberNavController())
}
