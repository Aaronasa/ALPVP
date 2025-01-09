package com.example.foodalp.View

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.foodalp.R
import com.example.foodalp.models.RestaurantModel
import com.example.foodalp.viewmodel.RestaurantViewModel
import androidx.compose.foundation.Image
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import coil.compose.SubcomposeAsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import coil.request.ImageRequest
import com.example.foodalp.AppContainer
import com.example.foodalp.enums.ListScreen

@Composable
fun RestaurantCard(
    onCardClick: () -> Unit,
    navController: NavController,
    restaurant: RestaurantModel,
    viewModel: RestaurantViewModel,
    modifier: Modifier = Modifier,
) {

    val token = getUserToken()

    Log.d("masuk ke display restaurantCard", "Restaurant name: ${restaurant.name}")
    Log.d("masuk ke display restaurantCard", "Restaurant phone: ${restaurant.phone}")
    Log.d("masuk ke display restaurantCard", "Restaurant address: ${restaurant.address}")
    Log.d("masuk ke display restaurantCard", "Restaurant image: ${restaurant.image}")
    Card(
        modifier = modifier
            .padding(8.dp)
            .clip(RoundedCornerShape(12.dp))
            .border(2.dp, Color(0xFF0051FF), RoundedCornerShape(12.dp)), // Blue border
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFF5F5F5) // Light gray background
        ),
        elevation = CardDefaults.cardElevation(4.dp) // Optional: Add slight elevation
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp), // Ensure padding inside the card
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Display restaurant image
            Box(
                modifier = Modifier
                    .size(50.dp)
                    .clip(CircleShape)
                    .background(Color(0xFFE0E0E0)), // Light gray for placeholder
                contentAlignment = Alignment.Center
            ) {
                SubcomposeAsyncImage(
                    model = restaurant.image,
                    contentDescription = "Restaurant Image",
                    loading = {
                        CircularProgressIndicator() // Show a loading indicator while the image loads
                    },
                    error = {
                        Text("Image failed to load") // Fallback UI if the image fails to load
                    },
                    contentScale = ContentScale.Crop, // Optional: Adjust the scaling of the image
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .clip(RoundedCornerShape(8.dp)) // Optional: Apply rounded corners
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                // Restaurant Name
                Text(
                    text = restaurant.name,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1A1A1A)
                )

                // Address
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_location_on_24),
                        contentDescription = "Location Icon",
                        tint = Color(0xFF8E8E8E),
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = restaurant.address,
                        fontSize = 14.sp,
                        color = Color(0xFF8E8E8E)
                    )
                    Button(
                        onClick = onCardClick,
                        modifier = Modifier
                            .fillMaxWidth(0.6f),
//                            .padding(horizontal = 20.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(
                                0xFF9C254D
                            )
                        )
                    ) {
                        Text("Update Restaurant", color = Color.White, fontSize = 16.sp)
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))
            }

        }
    }
}


