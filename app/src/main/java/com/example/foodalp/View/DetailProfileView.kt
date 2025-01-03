package com.example.foodalp.View

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import androidx.navigation.compose.rememberNavController
import com.example.foodalp.R

@Composable
fun DetailProfileView(navController: androidx.navigation.NavHostController) {
    val customFontFamily = FontFamily(
        Font(R.font.jua) // Replace with your font resource
    )
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.group_8),
            contentDescription = "Background orange",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
        Column(
            modifier = Modifier
                .fillMaxSize(),
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
                contentAlignment = Alignment.TopCenter
            ) {}

            // Profile Picture
            Spacer(modifier = Modifier.height(-50.dp)) // Adjusted to place half the profile picture into the header
            Box(
                modifier = Modifier
                    .size(150.dp) // Increased size of the profile picture
                    .background(Color.White, shape = CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ellipse_11), // Replace with your image resource
                    contentDescription = "Profile Picture",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(140.dp) // Adjusted size for the internal image
                        .clip(CircleShape)
                )
            }


            // Name and Location
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                "Rinaldy",
                fontWeight = FontWeight.Bold,
                fontSize = 30.sp,
                fontFamily = customFontFamily
            )
            Text(
                "Surabaya, ID",
                color = Color.Gray,
                fontSize = 20.sp,
                fontFamily = customFontFamily
            )

            // Email and Password
            Spacer(modifier = Modifier.height(24.dp))
            Column(
                horizontalAlignment = Alignment.Start,
                modifier = Modifier
                    .padding(horizontal = 32.dp)
                    .fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        "Email",
                        fontWeight = FontWeight.Bold,
                        color = Color.Gray,
                        fontSize = 20.sp,
                        fontFamily = customFontFamily
                    )
                    Text(
                        "Rinaldy123@gmail.com",
                        fontSize = 20.sp,
                        fontFamily = customFontFamily
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        "Password",
                        fontWeight = FontWeight.Bold,
                        color = Color.Gray,
                        fontSize = 20.sp,
                        fontFamily = customFontFamily
                    )
                    Text(
                        "********",
                        fontSize = 20.sp,
                        fontFamily = customFontFamily
                    )
                }
            }

            // Edit and Sign Out Buttons
            Spacer(modifier = Modifier.height(100.dp))
            Button(
                onClick = { /* Handle Edit */ },
                modifier = Modifier
                    .fillMaxWidth(0.6f),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF9C254D))
            ) {
                Text(
                    "Edit",
                    color = Color.White,
                    fontFamily = customFontFamily,
                    fontSize = 20.sp
                )
            }

            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = { /* Handle Sign Out */ },
                modifier = Modifier
                    .fillMaxWidth(0.6f)
                    .height(48.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF9C254D))
            ) {
                Text(
                    "Sign Out",
                    color = Color.White,
                    fontFamily = customFontFamily,
                    fontSize = 20.sp
                )
            }

        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DetailProfilePreview() {
    DetailProfileView(navController = rememberNavController())
}
