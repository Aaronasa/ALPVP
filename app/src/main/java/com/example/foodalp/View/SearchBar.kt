package com.example.foodalp.View

import androidx.compose.foundation.background
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.foodalp.R

@Composable
fun SearchBar(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Search Meals",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )

        Spacer(modifier = Modifier.height(16.dp))

        // **Main Meal Section**
        Text(
            text = "Main Meal",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
        LazyRow(modifier = Modifier.padding(vertical = 8.dp)) {
            items(10) { index -> // Replace 10 with your dynamic data size
                Box(
                    modifier = Modifier
                        .size(150.dp)
                        .padding(8.dp)
                        .background(Color.LightGray, RoundedCornerShape(12.dp))
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.food_placeholder),
                        contentDescription = "Main Meal $index",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // **Snack Section**
        Text(
            text = "Snack",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
        LazyRow(modifier = Modifier.padding(vertical = 8.dp)) {
            items(8) { index -> // Replace 8 with your dynamic data size
                Box(
                    modifier = Modifier
                        .size(150.dp)
                        .padding(8.dp)
                        .background(Color.LightGray, RoundedCornerShape(12.dp))
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.food_placeholder),
                        contentDescription = "Snack $index",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // **Souvenir Section**
        Text(
            text = "Souvenir",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
        LazyRow(modifier = Modifier.padding(vertical = 8.dp)) {
            items(6) { index -> // Replace 6 with your dynamic data size
                Box(
                    modifier = Modifier
                        .size(150.dp)
                        .padding(8.dp)
                        .background(Color.LightGray, RoundedCornerShape(12.dp))
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.food_placeholder),
                        contentDescription = "Souvenir $index",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }
        }
    }
}