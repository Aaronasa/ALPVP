package com.example.foodalp.View

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.foodalp.R
import com.example.foodalp.Route.ListScreen
import com.example.foodalp.uiStates.UserStatusUIState
import com.example.foodalp.viewmodels.UserViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterView(navController: NavController, viewModel: UserViewModel = viewModel()) {
    val customFontFamily = FontFamily(Font(R.font.jua))
    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    // Observe registration state from ViewModel
    val statusState = viewModel.statusState

    Box(modifier = Modifier.fillMaxHeight()) {
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
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
                    .padding(end = 16.dp),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.rasalanka),
                    contentDescription = "Logo",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
                Text(
                    text = "Welcome to Rasalanka",
                    fontSize = 20.sp,
                    color = Color(0xFF991E3D),
                    fontFamily = customFontFamily,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(top = 225.dp, start = 28.dp)
                )
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomEnd)
                    .background(
                        Color(0xFF991E3D),
                        shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
                    )
                    .padding(16.dp, 30.dp, 16.dp, 10.dp)
            ) {
                Column(
                    modifier = Modifier
                        .background(Color(0xFF991E3D))
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Create Account",
                        fontSize = 24.sp,
                        color = Color(0xFFFFFFFF),
                        fontFamily = customFontFamily,
                        modifier = Modifier.padding(bottom = 20.dp)
                    )
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        // Username input field
                        Text(
                            text = "Username",
                            fontSize = 14.sp,
                            color = Color(0xFFFFFFFF),
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
                                unfocusedIndicatorColor = Color.Transparent,
                                disabledIndicatorColor = Color.Transparent
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
                            fontSize = 14.sp,
                            color = Color(0xFFFFFFFF),
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
                                unfocusedIndicatorColor = Color.Transparent,
                                disabledIndicatorColor = Color.Transparent
                            ),
                            textStyle = androidx.compose.ui.text.TextStyle(
                                fontSize = 20.sp
                            ),
                            singleLine = true
                        )
                        Spacer(modifier = Modifier.height(16.dp))

                        // Password input field
                        Text(
                            text = "Password",
                            fontSize = 14.sp,
                            color = Color(0xFFFFFFFF),
                            fontFamily = customFontFamily,
                            modifier = Modifier.padding(bottom = 4.dp, start = 5.dp)
                        )
                        TextField(
                            value = password,
                            onValueChange = { password = it },
                            visualTransformation = PasswordVisualTransformation(),
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
                                unfocusedIndicatorColor = Color.Transparent,
                                disabledIndicatorColor = Color.Transparent
                            ),
                            textStyle = androidx.compose.ui.text.TextStyle(
                                fontSize = 20.sp
                            ),
                            singleLine = true
                        )
                    }

                    // Sign Up Button
                    Spacer(modifier = Modifier.height(25.dp))
                    Button(
                        onClick = {
                            // Trigger register action
                            viewModel.registerUser(username, email, password)
                        },
                        modifier = Modifier
                            .fillMaxWidth(0.6f)
                            .padding(bottom = 60.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFFE7C1BF),
                            contentColor = Color(0xFF0C094E)
                        ),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Text(
                            text = "Sign Up",
                            fontSize = 22.sp,
                            fontFamily = customFontFamily
                        )
                    }
                }
            }
        }
    }
    LaunchedEffect(statusState.value) {
        when (statusState.value) {
            is UserStatusUIState.Loading -> {
                Log.d("RegisterView", "Registration in progress...")
            }
            is UserStatusUIState.Error -> {
            }
            is UserStatusUIState.Success -> {
                Log.d("RegisterView", "Registration Successful! Navigating to LoginView.")
                navController.navigate(ListScreen.Loginview.name) {
                    // PopUp the RegisterView but prevent the backstack from being re-created
                    popUpTo(ListScreen.Registerview.name)
                    launchSingleTop = true // Ensures LoginView is only created once
                }
            }
            UserStatusUIState.Idle -> {
                Log.d("RegisterView", "Idle state, no action needed.")
            }
            else -> {}
        }
    }
}

