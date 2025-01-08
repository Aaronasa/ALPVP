package com.example.foodalp.View

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
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
fun ReviewCard() {
    val customFontFamily = FontFamily(
        Font(R.font.jua)
    )
    val rating = 5 // Rating value

    Box(
        modifier = Modifier
            .padding(16.dp) // Outer padding to separate from screen edges
            .clip(RoundedCornerShape(16.dp)) // Rounded corners for the card
            .background(Color.Gray) // Background color of the card
            .padding(8.dp) // Padding inside the card for a clean layout
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // Header row with user image, name, and stars
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(id = R.drawable.baseline_person_24),
                    contentDescription = "Profile Picture",
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

                Row(modifier = Modifier.padding(start = 8.dp)) {
                    repeat(5) { index ->
                        Icon(
                            painter = painterResource(
                                id = if (index < rating) R.drawable.baseline_star_24 else R.drawable.baseline_star_border_24
                            ),
                            contentDescription = "Star Icon",
                            tint = Color.Yellow,
                            modifier = Modifier.size(28.dp)
                        )
                    }
                }
            }

            // Review text
            Row {
                Text(
                    "This is a review",
                    color = Color.Black,
                    fontFamily = customFontFamily,
                    modifier = Modifier.padding(start = 8.dp, top = 8.dp),
                    fontSize = 26.sp
                )
            }

            // Delete button at the bottom-right
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                horizontalArrangement = Arrangement.End
            ) {
                Button(
                    onClick = {
                        println("Delete button clicked") // Replace with your delete functionality
                    },
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                ) {
                    Text("Delete", fontSize = 16.sp)
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewReviewCard() {
    ReviewCard()
}
