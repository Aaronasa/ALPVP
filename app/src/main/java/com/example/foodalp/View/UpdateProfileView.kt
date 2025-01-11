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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import com.example.foodalp.R
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.foodalp.Route.ListScreen
import com.example.foodalp.viewmodels.UserViewModel
import com.example.foodalp.uistates.UserStatusUIState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateProfileView(navController: androidx.navigation.NavHostController) {
    val userViewModel: UserViewModel = viewModel() // Get an instance of the ViewModel
    val statusState by userViewModel.statusState.observeAsState(UserStatusUIState.Idle)

    val customFontFamily = FontFamily(
        Font(R.font.jua) // Replace with your font resource
    )

    val token1 = getUserToken11()

    var username by remember { mutableStateOf("") } // State for username
    var email by remember { mutableStateOf("") } // State for email

    // Handle Update button click
    val onUpdateClick: () -> Unit = {
        val token = token1 // Retrieve the token from SharedPreferences or ViewModel
        if (!token.isNullOrEmpty()) {
            // Pass token in the header, and send username and email in the body
            userViewModel.updateUser(
                token = token1.toString(),
                username = username,
                email = email
            )
        } else {
            // Handle case where token is missing
            Log.e("UpdateProfile", "Token is missing!")
        }
    }

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
                        shape = RoundedCornerShape(bottomEnd = 16.dp, bottomStart = 16.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    "Update Profile",
                    fontSize = 36.sp,
                    fontFamily = customFontFamily,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFFFFFFFF)
                )
            }

            // Profile Image
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

            // Input Fields for Username and Email
            Spacer(modifier = Modifier.height(24.dp))
            Column(
                horizontalAlignment = Alignment.Start,
                modifier = Modifier
                    .padding(horizontal = 32.dp)
                    .fillMaxWidth()
            ) {
                // Username input field
                Text(
                    text = "Username",
                    fontSize = 20.sp,
                    color = Color(0xFF0F0A3F),
                    fontFamily = customFontFamily,
                    modifier = Modifier.padding(bottom = 4.dp, start = 5.dp)
                )
                TextField(
                    value = username,
                    onValueChange = { username = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            color = Color(0x33000000),
                            shape = RoundedCornerShape(16.dp)
                        ),
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = Color.Transparent,
                        cursorColor = Color.Black,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    ),
                    textStyle = androidx.compose.ui.text.TextStyle(
                        fontSize = 20.sp
                    ),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Email input field
                Text(
                    text = "Email",
                    fontSize = 20.sp,
                    color = Color(0xFF0F0A3F),
                    fontFamily = customFontFamily,
                    modifier = Modifier.padding(bottom = 4.dp, start = 5.dp)
                )
                TextField(
                    value = email,
                    onValueChange = { email = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            color = Color(0x33000000),
                            shape = RoundedCornerShape(16.dp)
                        ),
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = Color.Transparent,
                        cursorColor = Color.Black,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    ),
                    textStyle = androidx.compose.ui.text.TextStyle(
                        fontSize = 20.sp
                    ),
                    singleLine = true
                )
            }

            // Update Button
            Spacer(modifier = Modifier.height(50.dp))
            Button(
                onClick = onUpdateClick,  // Trigger update when clicked
                modifier = Modifier
                    .fillMaxWidth(0.6f),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF9C254D))
            ) {
                Text(
                    "Update",
                    color = Color.White,
                    fontFamily = customFontFamily,
                    fontSize = 20.sp
                )
            }

            // Handle UI State: Show loading, error, or success messages
            when (statusState) {
                is UserStatusUIState.Loading -> {
                    // Show loading spinner or text here
                    Text("Loading...", color = Color.Gray, fontSize = 18.sp)
                }
                is UserStatusUIState.Success -> {
                    // Show success message
                    Text("Profile updated successfully!", color = Color.Green, fontSize = 18.sp)
                    // Navigate back to the HomePage after success
                    LaunchedEffect(statusState) {
                        navController.navigate(ListScreen.Loginview.name) {
                            popUpTo(ListScreen.UpdateProfileView.name) { inclusive = true }
                        }
                    }
                }
                is UserStatusUIState.Error -> {
                    // Show error message
                    Text("Error: ${(statusState as UserStatusUIState.Error).message}", color = Color.Red, fontSize = 18.sp)
                }
                else -> {}
            }
        }
    }
}

@Composable
fun getUserToken11(): String? {
    val context = LocalContext.current
    val sharedPreferences = context.getSharedPreferences("user_session", Context.MODE_PRIVATE)
    return sharedPreferences.getString("token", null)
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun UpdateProfileView1() {
    UpdateProfileView(navController = rememberNavController())
}

