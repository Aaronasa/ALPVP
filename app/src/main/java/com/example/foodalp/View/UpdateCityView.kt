package com.example.foodalp.View

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.foodalp.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateCityView(navController: NavHostController) {
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
                contentAlignment = Alignment.Center
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        "Update City",
                        fontSize = 36.sp,
                        fontFamily = customFontFamily,
                        fontWeight = FontWeight.SemiBold,
                        color = Color(0xFFFFFFFF)
                    )
                }
            }
            Spacer(modifier = Modifier.height(24.dp))
            Column(
                horizontalAlignment = Alignment.Start,
                modifier = Modifier
                    .padding(horizontal = 32.dp)
                    .fillMaxWidth()
            ) {
                Spacer(modifier = Modifier.height(16.dp))
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    // Username input field
                    Text(
                        text = "City Name",
                        fontSize = 20.sp,
                        color = Color(0xFF0F0A3F),
                        fontFamily = customFontFamily,
                        modifier = Modifier.padding(bottom = 4.dp, start = 5.dp)
                    )
                    TextField(
                        value = "",
                        onValueChange = {  },
                        placeholder = { Text(
                            text = "Input City Name",
                            color = Color(0x80000000),
                            fontFamily = customFontFamily
                        ) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                color = Color(0x33000000),
                                shape = RoundedCornerShape(16.dp)
                            ),
                        colors = TextFieldDefaults.textFieldColors(
                            containerColor = Color.Transparent,
                            cursorColor = Color.Black,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            disabledIndicatorColor = Color.Transparent
                        ),
                        textStyle = TextStyle(
                            fontSize = 20.sp
                        ),
                        singleLine = true
                    )
                    Spacer(modifier = Modifier.height(16.dp))


                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "City Image",
                        fontSize = 20.sp,
                        color = Color(0xFF0F0A3F),
                        fontFamily = customFontFamily,
                        modifier = Modifier.padding(bottom = 4.dp, start = 5.dp)
                    )
                    Button(
                        onClick = { /* Handle Image Upload */ },
                        modifier = Modifier
                            .wrapContentWidth()
                            .height(36.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF9C254D))
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                "Upload Image",
                                color = Color.White,
                                fontFamily = customFontFamily,
                                fontSize = 14.sp
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Image(
                                painter = painterResource(id = R.drawable.baseline_file_upload_24),
                                contentDescription = "Upload Icon",
                                modifier = Modifier.size(25.dp)
                            )
                        }
                    }

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
                    "Update",
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
fun UpdateCityPreView() {
    UpdateCityView(navController = rememberNavController())
}
