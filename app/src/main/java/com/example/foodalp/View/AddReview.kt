package com.example.foodalp.View

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.foodalp.R

@Composable
fun AddReview() {
    val customFontFamily = FontFamily(Font(R.font.jua))

    var reviewText by remember { mutableStateOf("") } // State for review text
    var rating by remember { mutableStateOf(0) } // State for star rating (1-5)

    Box(
        modifier = Modifier
            .padding(16.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(Color.Gray)
            .padding(8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // User info row
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(id = R.drawable.baseline_person_24),
                    contentDescription = "Profile icon",
                    modifier = Modifier
                        .size(40.dp)
                        .clip(RoundedCornerShape(50))
                )
                Text(
                    "Rinaldy",
                    color = Color.Black,
                    fontFamily = customFontFamily,
                    modifier = Modifier.padding(start = 8.dp, top = 2.dp),
                    fontSize = 30.sp
                )

                // Star Rating Row
                Row(
                    modifier = Modifier
                        .padding(vertical = 16.dp)
                        .fillMaxWidth()
                        .padding(start = 8.dp),
                    horizontalArrangement = Arrangement.Start
                ) {
                    for (i in 1..5) {
                        Icon(
                            painter = painterResource(
                                id = if (i <= rating) R.drawable.baseline_star_24 // Filled star
                                else R.drawable.baseline_star_border_24 // Empty star
                            ),
                            contentDescription = "Star $i",
                            tint = Color.Yellow,
                            modifier = Modifier
                                .size(32.dp)
                                .clickable { rating = i } // Update the rating state
                        )
                    }
                }
            }

            // User Input for Review Text
            TextField(
                value = reviewText,
                onValueChange = { reviewText = it }, // Update review text state
                label = { Text("Write your review here", fontFamily = customFontFamily) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp)
            )

            // Send Button
            Button(
                onClick = {
                    // Handle the send action (e.g., submit review and rating)
                    println("Review submitted: $reviewText")
                    println("Rating submitted: $rating")
                },
                modifier = Modifier
                    .align(Alignment.End)
                    .padding(top = 16.dp)
            ) {
                Text("Send", fontFamily = customFontFamily)
            }
        }
    }
}

@Preview
@Composable
fun PreviewAddReview() {
    AddReview()
}
