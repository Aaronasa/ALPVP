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
import com.example.foodalp.models.UpdateReviewRequest

@Composable
fun UpdateReview(
    navController: NavHostController,
    reviewId: Int,
    token: String,
    username: String,
    role: Int,
//    userId: Int,
    ReviewViewModel: ReviewViewModel = viewModel(),
) {
    val customFontFamily = FontFamily(Font(R.font.jua))


    var reviewText by remember { mutableStateOf("") }
    var ratingInput by remember { mutableStateOf("") } // State for user rating input
    val token = getUserToken()
    var isDataLoaded by remember { mutableStateOf(false) }
    val uiState by ReviewViewModel.uiState.collectAsState()

    if(role == 2) {
        LaunchedEffect(reviewId) {
            Log.d("UpdateRestaurantView", "Loading restaurant data for ID: $reviewId")
            if (!isDataLoaded) {
                Log.d("UpdateRestaurantView", "Check load data => id: $reviewId dan token: $token")
                if (token != null) {
                    ReviewViewModel.fetchReviewById(
                        token = token, // Replace with actual token logic
                        reviewId = reviewId
                    ) { review ->
                        Log.d("UpdateRestaurantView", "Loaded restaurant: $review")
                        review?.let {
                            reviewText = it.content
                            ratingInput = it.rating.toString()
                            isDataLoaded = true
                        }
                    }
                }
            }
        }
    }
    else if (role == 1){
        LaunchedEffect(reviewId) {
            Log.d("UpdateRestaurantView", "Loading restaurant data for ID: $reviewId")
            if (!isDataLoaded) {
                Log.d("UpdateRestaurantView", "Check load data => id: $reviewId dan token: $token")
                if (token != null) {
                    ReviewViewModel.fetchReviewByIdAdmin(
                        token = token, // Replace with actual token logic
                        reviewId = reviewId
                    ) { review ->
                        Log.d("UpdateRestaurantView", "Loaded restaurant: $review")
                        review?.let {
                            reviewText = it.content
                            ratingInput = it.rating.toString()
                            isDataLoaded = true
                        }
                    }
                }
            }
        }
    }



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
            modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Top Background Shape
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
                    .background(
                        Color(0xFF991E3D),
                        shape = RoundedCornerShape(bottomEnd = 16.dp, bottomStart = 16.dp)
                    ), contentAlignment = Alignment.Center
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
                        text = username,
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


                        val content = content;
                        val rating = rating.toInt();

                        if (token != null) {
                            if(role == 2) {
                                ReviewViewModel.updateReview(
                                    token, reviewId, content, rating
                                )
                            }
                            else if (role == 2){
                                ReviewViewModel.updateReviewAdmin(
                                    token, reviewId, content, rating
                                )
                            }
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
                    "Submit", color = Color.White, fontSize = 20.sp
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

