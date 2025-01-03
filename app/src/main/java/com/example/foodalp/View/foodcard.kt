package com.example.foodalp.View

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.foodalp.R
import java.time.format.TextStyle


@Composable
fun FoodCard() {
    val customFontFamily = FontFamily(
        Font(R.font.jua)
    )

    Box(
        modifier = Modifier
            .padding(16.dp) // Outer padding to separate from screen edges
            .clip(RoundedCornerShape(16.dp)) // Rounded corners for the card
            .background(Color.White) // Background color of the card
            .padding(8.dp) // Padding inside the card for a clean layout
    ) {
        Column(
            modifier = Modifier.size(width = 250.dp, height = 300.dp)
        ) {
            // Image section
            Image(
                painter = painterResource(id = R.drawable.ic_launcher_background), // Replace with your image
                contentDescription = "Lake Braise",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp) // Set height for the image
                    .clip(RoundedCornerShape(12.dp)) // Rounded corners for the image
            )

            // Text
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 5.dp, top = 14.dp, end = 5.dp),
//                verticalArrangement = Arrangement.Center,
            ) {
                // Title text
                Text(
                    text = "Lake Braise",
                    color = Color.Black,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = customFontFamily
                )
            }
        }
    }
}

@Preview
@Composable
fun PreviewFoodCard() {
    FoodCard()
}

