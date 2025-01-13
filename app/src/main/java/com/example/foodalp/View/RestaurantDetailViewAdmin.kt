package com.example.foodalp.View

import UserModel
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.SubcomposeAsyncImage
import com.example.foodalp.R
import com.example.foodalp.enums.ListScreen
import com.example.foodalp.models.ReviewModel
import com.example.foodalp.uistates.ReviewState
import com.example.foodalp.viewmodel.RestaurantViewModel
import com.example.foodalp.viewmodel.ReviewViewModel
import com.example.foodalp.viewmodels.UserViewModel

@Composable
fun RestaurantDetailViewAdmin(
    navController: NavController,
    restauranId: Int,
    token: String,
    username: String,
    userId: Int,
    role: Int,
    RestaurantviewModel: RestaurantViewModel = viewModel(),
    ReviewViewModel: ReviewViewModel = viewModel(),
    userViewModel: UserViewModel = viewModel()
) {
    val user by userViewModel.AllUser.collectAsState()

    LaunchedEffect(Unit) {
        if(token != null){
            userViewModel.getallUser(token)
        }
    }

    val Review by ReviewViewModel.admin.collectAsState()
    val UIstateReview by ReviewViewModel.UIstate.collectAsState()

    val ReviewAdmin by ReviewViewModel.admin.collectAsState()

    Log.d("Restaurant Detail View", "Review response: $Review")

    var restaurantName by remember { mutableStateOf("") }
    var restaurantAddress by remember { mutableStateOf("") }
    var restaurantPhone by remember { mutableStateOf("") }
    var image by remember { mutableStateOf("") }

    var isDataLoaded by remember { mutableStateOf(false) }
    var isReviewLoaded by remember { mutableStateOf(false) }

    LaunchedEffect(restauranId) {
        Log.d("RestaurantDetailView", "Loading restaurant data for ID: $restauranId")
        if (!isDataLoaded) {
            Log.d(
                "RestaurantDetailView",
                "Check load data => id: $restauranId dan token: $token"
            )
            RestaurantviewModel.FetchRestaurantByIdAdmin(
                token = token,
                restaurantId = restauranId
            ) { restaurant ->
                Log.d("RestaurantDetailView", "Loaded restaurant: $restaurant")
                restaurant?.let {
                    restaurantName = it.name
                    restaurantAddress = it.address
                    restaurantPhone = it.phone
                    image = it.image
                    isDataLoaded = true
                }
            }
        }
        if (!isReviewLoaded) {
            ReviewViewModel.fetchAllReviewsAdmin(
                token = token
            )
            isReviewLoaded = true
        }
    }


    Log.d("restaurantDetailView", "restaurantName: $restaurantName")
    Log.d("restaurantDetailView", "restaurantAddress: $restaurantAddress")
    Log.d("restaurantDetailView", "restaurantPhone: $restaurantPhone")
    Log.d("restaurantDetailView", "image: $image")

    val customFontFamily = FontFamily(
        Font(R.font.jua)
    )

    Column {
        // Restaurant Image
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
        ) {
            SubcomposeAsyncImage(
                model = image,
                contentDescription = "Restaurant Image",
                loading = {
                    CircularProgressIndicator()
                },
                error = {
                    Text("Image failed to load")
                },
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
            IconButton(
                onClick = { navController.navigate(ListScreen.CityDetailViewAdmin.name + "/${restauranId}/${token}/${username}/${userId}/${role}") },
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

        // Restaurant Info
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // Name and Buttons
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = restaurantName,
                    fontFamily = customFontFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp,
                    color = Color.Black,
                    style = MaterialTheme.typography.titleMedium
                )
                Row {
                    Button(
                        onClick = { navController.navigate(ListScreen.UpdateRestaurantView.name + "/${restauranId}/${token}") },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF9C254D)),
//                            contentPadding =  PaddingValues(4.dp),
                        modifier = Modifier.padding(end = 8.dp),
                    ) {
                        Text(
                            "Edit",
                            color = Color.White,
                            fontFamily = customFontFamily,
                            fontWeight = FontWeight.Bold,
                        )
                    }
                    Button(
                        onClick = {
                            if (restauranId != null && token.isNotEmpty()) {
                                RestaurantviewModel.deleteRestaurant(restauranId, token)
                                Toast.makeText(
                                    navController.context,
                                    "Restaurant Delete Successfully",
                                    Toast.LENGTH_SHORT
                                ).show()
                                navController.popBackStack()
                            } else {
                                Toast.makeText(
                                    navController.context,
                                    "Failed to delete. Invalid data.",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF9C254D)),
//                            contentPadding =  PaddingValues(6.dp)
                    ) {
                        Text(
                            "Delete",
                            color = Color.White,
                            fontFamily = customFontFamily,
                            fontWeight = FontWeight.Bold,
                        )
                    }
                }
            }


            Spacer(modifier = Modifier.height(8.dp))

            // Address and No Tip
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.LocationOn,
                    contentDescription = "Location Icon",
                    tint = Color.Gray,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = restaurantAddress,
                    fontFamily = customFontFamily,
                    fontSize = 16.sp,
                    color = Color.Gray
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.Call,
                    contentDescription = "call",
                    tint = Color.Gray,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = restaurantPhone,
                    fontFamily = customFontFamily,
                    fontSize = 16.sp,
                    color = Color.Gray
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Menu Item
            Text(
                text = "Menu Item",
                fontFamily = customFontFamily,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Description
            Text(
                text = "description",
                fontFamily = customFontFamily,
                fontWeight = FontWeight.W200,
                fontSize = 16.sp,
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Price
            Text(
                text = "Price",
                fontFamily = customFontFamily,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "price",
                fontFamily = customFontFamily,
                fontWeight = FontWeight.W200,
                fontSize = 16.sp,
                color = Color.Gray
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Review",
                fontFamily = customFontFamily,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
        }
        when (UIstateReview) {
            is ReviewState.Loading -> {
                CircularProgressIndicator()  // Show loading while fetching restaurants
            }

            is ReviewState.Failed -> {
                val errorMessage =
                    (UIstateReview as ReviewState.Failed).errorMessage
                Text(errorMessage)  // Display the error message
            }

            is ReviewState.Success -> {
                val reviews = (UIstateReview as ReviewState.Success).data
                if (reviews.isEmpty()) {
                    Text("No reviews available.")
                } else {
                    ReviewGridAdmin(
                        token = token,
                        navController = navController,
                        reviews = Review,
                        restauranId = restauranId,
                        role = role,
                        userid = userId,
                        username = username,
                        user = user,
                        reviewViewModel = ReviewViewModel
                    )
                }
            }

            is ReviewState.Start -> {
                Text("Welcome!")
            }
        }
    }
}

@Composable
fun ReviewGridAdmin(
    token: String,
    navController: NavController,
    reviews: List<ReviewModel>,
    restauranId: Int,
    username: String,
    role: Int,
    userid: Int,
    user: List<UserModel>,
    reviewViewModel: ReviewViewModel,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        items(reviews) { review ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                ReviewCardAdmin(
                    UpdateClick = { navController.navigate(ListScreen.UpdateReviewView.name + "/${review.id}/${token}/${username}/${role}") },
                    navController = navController,
                    review = review,
                    restauranId = restauranId,
                    username = username,
                    token = token,
                    userId = userid,
                    role = role,
                    ReviewViewModel = reviewViewModel,
                    modifier = Modifier
                        .weight(1f)
                        .padding(8.dp)
                )
            }

        }
    }
}

