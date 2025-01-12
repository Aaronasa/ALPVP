package com.example.foodalp.View

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.SubcomposeAsyncImage
import com.example.foodalp.enums.ListScreen
import com.example.foodalp.models.FoodModel
import com.example.foodalp.uistates.FoodByIdState
import com.example.foodalp.uistates.FoodState
import com.example.foodalp.viewmodel.FoodViewModel

@Composable
fun FoodDetailView(
    navController: NavController,
    foodId: Int,
    token: String,
    username: String,
    userId: Int,
    role: Int,
    foodViewModel: FoodViewModel = viewModel()
) {
    val foodByIdState by foodViewModel.foodById.collectAsState()

    LaunchedEffect(Unit) {
        foodViewModel.fetchFoodById(token, foodId)
    }

    Column(modifier = Modifier.fillMaxSize()) {
        when (foodByIdState) {
            is FoodByIdState.Loading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
            }

            is FoodByIdState.Success -> {
                val food = (foodByIdState as FoodByIdState.Success).data

                // Food Image
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                ) {
                    SubcomposeAsyncImage(
                        model = food.image,
                        contentDescription = "Food Image",
                        loading = { CircularProgressIndicator() },
                        error = { Text("Image failed to load") },
                        modifier = Modifier.fillMaxSize()
                    )
                    IconButton(
                        onClick = { navController.navigate(ListScreen.CityDetailViewAdmin.name + "/$token/$username/$userId/$role") },
                        modifier = Modifier
                            .align(Alignment.TopStart)
                            .padding(8.dp)
                            .size(32.dp)
                            .background(Color.Black.copy(alpha = 0.5f), CircleShape)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Close",
                            tint = Color.White
                        )
                    }
                }

                // Food Info
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Text(
                        text = food.name,
                        fontSize = 24.sp,
                        color = Color.Black,
                        style = MaterialTheme.typography.titleMedium
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = food.description,
                        fontSize = 16.sp,
                        color = Color.Gray
                    )

                    Spacer(modifier = Modifier.height(8.dp))

//                    Text(
//                        text = "Price: $${food.price}",
//                        fontSize = 20.sp,
//                        fontWeight = FontWeight.Bold,
//                        color = Color.Black
//                    )
                }
            }

            is FoodByIdState.Failed -> {
                val errorMessage = (foodByIdState as FoodByIdState.Failed).errorMessage
                Text(
                    text = errorMessage,
                    color = Color.Red,
                    modifier = Modifier
                        .padding(16.dp)
                        .align(Alignment.CenterHorizontally)
                )
            }

            FoodByIdState.Start -> {
                // Initial state, no UI needed
            }
        }
    }
}