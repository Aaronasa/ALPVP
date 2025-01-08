package com.example.foodalp.View

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.foodalp.R
import com.example.foodalp.models.CreateReviewRequest
import com.example.foodalp.viewmodel.ReviewViewModel
import android.widget.Toast

@Composable
fun AddReview(
    navController: NavHostController,
    ReviewViewModel: ReviewViewModel = viewModel(),
) {
    val customFontFamily = FontFamily(Font(R.font.jua))
    var reviewText by remember { mutableStateOf("") }
    var ratingInput by remember { mutableStateOf("") } // State for user rating input
    val token = getUserToken()
    val uiState by ReviewViewModel.uiState.collectAsState()

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        // Background Image
        Image(
            painter = painterResource(id = R.drawable.group_8),
            contentDescription = "Background",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier.fillMaxSize(),
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
                    "Add Review",
                    fontSize = 36.sp,
                    color = Color.White,
                    fontFamily = customFontFamily
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Column(
                horizontalAlignment = Alignment.Start,
                modifier = Modifier
                    .padding(horizontal = 32.dp)
                    .fillMaxWidth()
            ) {
                // User Info
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "Rinaldy",
                        color = Color.Black,
                        fontSize = 24.sp,
                        fontFamily = customFontFamily
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Rating Input
                TextField(
                    value = ratingInput,
                    onValueChange = {
                        if (it.isEmpty() || (it.toIntOrNull() in 1..5)) {
                            ratingInput = it
                        }
                    },
                    label = { Text("Rate (1-5)") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Review Text Field
                TextField(
                    value = reviewText,
                    onValueChange = { reviewText = it },
                    label = { Text("Write your review here") },
                    modifier = Modifier.fillMaxWidth()
                )
            }

            Spacer(modifier = Modifier.height(100.dp))

            // Submit Button
            val context = LocalContext.current
            Button(
                onClick = {
                    val rating = ratingInput.toIntOrNull()
                    val content = reviewText.trim()

                    // Validate input
                    if (content.isNotEmpty() && rating != null && rating in 1..5) {
                        val request = CreateReviewRequest(
                            userId = 1, // Replace with actual user ID logic
                            restaurantId = 7, // Replace with actual restaurant ID logic
                            content = content,
                            rating = rating.toInt()
                        )

                        if (token != null) {
                            ReviewViewModel.createReview(token, request)
                        }
                        Toast.makeText(
                            navController.context,
                            "Review Submitted Successfully",
                            Toast.LENGTH_SHORT
                        ).show()
                        navController.popBackStack()

                    } else {
                        Toast.makeText(
                            navController.context,
                            "Please complete all fields correctly!",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                },
                modifier = Modifier.fillMaxWidth(0.6f),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF9C254D))
            ) {
                Text(
                    "Submit",
                    color = Color.White,
                    fontSize = 20.sp
                )
            }
            if (uiState.isLoading) {
                CircularProgressIndicator()
            } else if (uiState.errorMessage != null) {
                Text(uiState.errorMessage!!, color = Color.Red)
            }

        }
    }
}

//@Preview(showBackground = true, showSystemUi = true)
//@Composable
//fun PreviewAddReview() {
//    AddReview()
//}
