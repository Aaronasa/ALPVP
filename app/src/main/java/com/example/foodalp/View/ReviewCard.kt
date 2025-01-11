package com.example.foodalp.View

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.foodalp.R
import com.example.foodalp.models.ReviewModel
import com.example.foodalp.viewmodel.RestaurantViewModel
import com.example.foodalp.viewmodel.ReviewViewModel

@Composable
fun ReviewCard(
    UpdateClick: () -> Unit,
    navController: NavController,
    review: ReviewModel,
    restauranId: Int,
    username: String,
    token: String,
    userId: Int,
    role: Int,
    ReviewViewModel: ReviewViewModel,
    RestaurantViewModel: RestaurantViewModel = viewModel(),
    modifier: Modifier = Modifier,
) {

    val Restaurant by RestaurantViewModel.Restaurant.collectAsState()
    val UIstate by RestaurantViewModel.UIstate.collectAsState()

    val customFontFamily = FontFamily(
        Font(R.font.jua)
    )
    val rating = review.rating
    val restaurantIDDefault = review.restaurantId
    val userIds = review.userId

    Log.d("ReviewCard", "Restaurant id dari review: $restaurantIDDefault")
    Log.d("ReviewCard", "Restaurant Id dr card: $restauranId")


    if (restaurantIDDefault == restauranId) {

        val reviewId = review.id
        Log.d("ReviewCard", "Review Id: $reviewId")

        Card(
            modifier = Modifier
                .fillMaxWidth() // Ensures the card takes the full width of the container
                .padding(horizontal = 16.dp, vertical = 8.dp) // Consistent padding
                .clip(RoundedCornerShape(16.dp))
                .border(1.dp, Color.Gray, RoundedCornerShape(16.dp)), // Gray border
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            ),
            elevation = CardDefaults.cardElevation(4.dp) // Subtle shadow
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp) // Inner padding for spacing
            ) {
                // Header row with user image, name, and stars
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        painter = painterResource(id = R.drawable.baseline_person_24),
                        contentDescription = "Profile Picture",
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                            .background(Color(0xFFE0E0E0))
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = username,
                                color = Color.Black,
                                fontFamily = customFontFamily,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.width(8.dp)) // Space between username and stars
                            Row {
                                repeat(5) { index ->
                                    Icon(
                                        painter = painterResource(
                                            id = if (index < rating) R.drawable.baseline_star_24 else R.drawable.baseline_star_border_24
                                        ),
                                        contentDescription = "Star Icon",
                                        tint = Color(0xFFFFC107),
                                        modifier = Modifier.size(20.dp)
                                    )
                                }
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Review text
                Text(
                    text = review.content,
                    color = Color(0xFF333333),
                    fontFamily = customFontFamily,
                    fontSize = 14.sp,
                    modifier = Modifier.padding(top = 4.dp)
                )

                Spacer(modifier = Modifier.height(12.dp))

                if (role == 2) {
                    if (userIds == userId) {
                        // Update and Delete buttons
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.End // Align buttons to the end
                        ) {
                            Button(
                                onClick = UpdateClick,
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color(0xFF9C254D),
                                    contentColor = Color.White
                                ),
                                modifier = Modifier
                                    .clip(RoundedCornerShape(8.dp))
                                    .padding(end = 8.dp)
                                    .size(100.dp, 36.dp) // Smaller button size
                            ) {
                                Text(
                                    text = "Update",
                                    fontFamily = customFontFamily,
                                    fontSize = 14.sp
                                )
                            }
                            Button(
                                onClick = {
                                    Log.d("Review Card", "Sebelum masuk ke view model: $reviewId")
                                    if (reviewId != null && token.isNotEmpty()) {
                                        ReviewViewModel.deleteReview(token, reviewId)
                                        Toast.makeText(
                                            navController.context,
                                            "Review Delete Successfully",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    } else {
                                        Toast.makeText(
                                            navController.context,
                                            "Failed to delete. Invalid data.",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color(0xFF9C254D),
                                    contentColor = Color.White
                                ),
                                modifier = Modifier
                                    .clip(RoundedCornerShape(8.dp))
                                    .size(100.dp, 36.dp)
                            ) {
                                Text(
                                    text = "Delete",
                                    fontFamily = customFontFamily,
                                    fontSize = 14.sp
                                )
                            }
                        }
                    }
                } else if (role == 1){
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End // Align buttons to the end
                    ) {
                        Button(
                            onClick = UpdateClick,
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFF9C254D),
                                contentColor = Color.White
                            ),
                            modifier = Modifier
                                .clip(RoundedCornerShape(8.dp))
                                .padding(end = 8.dp)
                                .size(100.dp, 36.dp) // Smaller button size
                        ) {
                            Text(
                                text = "Update",
                                fontFamily = customFontFamily,
                                fontSize = 14.sp
                            )
                        }
                        Button(
                            onClick = {
                                Log.d("Review Card", "Sebelum masuk ke view model: $reviewId")
                                if (reviewId != null && token.isNotEmpty()) {
                                    ReviewViewModel.deleteReviewAdmin(token, reviewId)
                                    Toast.makeText(
                                        navController.context,
                                        "Review Delete Successfully",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                } else {
                                    Toast.makeText(
                                        navController.context,
                                        "Failed to delete. Invalid data.",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFF9C254D),
                                contentColor = Color.White
                            ),
                            modifier = Modifier
                                .clip(RoundedCornerShape(8.dp))
                                .size(100.dp, 36.dp)
                        ) {
                            Text(
                                text = "Delete",
                                fontFamily = customFontFamily,
                                fontSize = 14.sp
                            )
                        }
                    }
                }
            }
        }


    } else {
        Log.d("ReviewCard", "Skipping review for restaurantId: $restaurantIDDefault")
    }
}


