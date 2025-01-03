package com.example.foodalp.View

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.foodalp.R
import com.example.foodalp.Route.ListScreen
import com.example.foodalp.viewmodels.UserViewModel
import com.example.foodalp.uiStates.UserStatusUIState
import java.time.format.TextStyle
import androidx.compose.ui.platform.LocalContext


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginView(
    navController: NavHostController,
    viewModel: UserViewModel = viewModel()
) {
    val customFontFamily = FontFamily(
        Font(R.font.jua)
    )
    val context = LocalContext.current // Ensure context is available

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val statusState = viewModel.statusState.observeAsState(UserStatusUIState.Idle)

    Box(modifier = Modifier.fillMaxHeight()) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color.White)
        ) {
            // Background image and logo setup...
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

            // Login form
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
                        text = "Login Account",
                        fontSize = 24.sp,
                        color = Color(0xFFFFFFFF),
                        fontFamily = customFontFamily,
                        modifier = Modifier.padding(bottom = 20.dp)
                    )

                    // Email TextField
                    TextField(
                        value = email,
                        onValueChange = { email = it },
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                color = Color.Black.copy(alpha = 0.2f),
                                shape = RoundedCornerShape(16.dp)
                            ),
                        textStyle = androidx.compose.ui.text.TextStyle(
                            fontSize = 20.sp,
                            color = Color.White
                        ),
                        singleLine = true
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Password TextField
                    TextField(
                        value = password,
                        onValueChange = { password = it },
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                color = Color(0x33000000),
                                shape = RoundedCornerShape(16.dp)
                            ),
                        singleLine = true
                    )

                    Spacer(modifier = Modifier.height(25.dp))

                    // Sign In Button
                    Button(
                        onClick = {
                            if (email.isNotBlank() && password.isNotBlank()) {
                                // Pass context to the ViewModel if needed
                                viewModel.loginUser(email, password, context)
                            } else {
                                // Show an error or feedback that fields cannot be empty
                                Toast.makeText(context, "Email and password are required", Toast.LENGTH_SHORT).show()
                            }
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
                            text = "Sign In",
                            fontSize = 22.sp,
                            fontFamily = customFontFamily
                        )
                    }

                    // Show loading spinner while logging in
                    when (statusState.value) {
                        is UserStatusUIState.Loading -> {
                            CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
                        }
                        is UserStatusUIState.Success -> {
                            LaunchedEffect(Unit) {
                                navController.navigate(ListScreen.HomePage.name) {
                                    popUpTo(ListScreen.Loginview.name) { inclusive = true }
                                }
                            }
                        }
                        is UserStatusUIState.Error -> {
                            val errorMessage = (statusState.value as UserStatusUIState.Error).message
                            Text(text = "Error: $errorMessage", color = Color.Red)
                        }
                        else -> { /* Idle State */ }
                    }
                }
            }
        }
    }
}
