package com.example.foodalp.View

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
fun foodcard (){
    val customFontFamily = FontFamily(
        Font(R.font.jua)
    )
    Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF5F5F5)),
    contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .width(300.dp)
                .height(400.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(Color.White)
        ) {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                // Image section
                Image(
                    painter = painterResource(id = R.drawable.asset1), // Replace with your image
                    contentDescription = "Lake Braise",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp)
                )
                // Gradient overlay (optional, for better text contrast)
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(80.dp)
                        .background(
                            Brush.verticalGradient(
                                listOf(Color.Transparent, Color.Black),
                                startY = 0f
                            )
                        ),
                    contentAlignment = Alignment.BottomStart
                ) {
//                    Text(
//                        text = "Lake Braise",
//                        style = TextStyle(
//                            color = Color.White,
//                            fontSize = 24.sp,
//                            fontWeight = FontWeight.Bold
//                        ),
//                        modifier = Modifier.padding(16.dp)
//                    )
                }

            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun preview(){
    foodcard()
}