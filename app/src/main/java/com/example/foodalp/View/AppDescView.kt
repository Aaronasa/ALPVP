package com.example.foodalp.View

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.foodalp.R

@Composable
fun AppDescView(
    navController: NavHostController
){
    val customFontFamily = FontFamily(
        Font(R.font.jua)
    )
    val redColor = Color(0xFF991E3D)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(redColor)
    ) {
        Button(
            onClick = {
                navController.navigate("MenujuHomepage1View")
            },
            modifier = Modifier
                .fillMaxSize()
                .padding(0.dp)
                .background(redColor),
            shape = RoundedCornerShape(0.dp),
            contentPadding = PaddingValues(0.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxSize()
                    .background(redColor),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                // Gambar Logo
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            Color.White,
                            shape = RoundedCornerShape(bottomEnd = 16.dp, bottomStart = 16.dp)
                        )
                        .padding(16.dp, 40.dp, 16.dp, 10.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.rasalanka),
                        contentDescription = "Logo",
                        modifier = Modifier.size(width = 350.dp, height = 200.dp),
                        contentScale = ContentScale.Crop
                    )
                }

                // Teks deskripsi aplikasi
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 46.dp, start = 10.dp, end = 10.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("Hello!",
                        color = Color.White,
                        fontSize = 36.sp,
                        fontFamily = customFontFamily,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.padding(bottom = 10.dp)
                    )
                    Text("Welcome to Rasalanka",
                        color = Color.White,
                        fontSize = 35.sp,
                        fontFamily = customFontFamily,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.padding(bottom = 26.dp)
                    )
                    Text(
                        text = "Rasalanka is a culinary guide app that helps tourists discover and compare the best restaurants to enjoy regional specialties and local souvenirs.",
                        color = Color.White,
                        fontSize = 18.sp,
                        fontFamily = customFontFamily,
                        fontWeight = FontWeight.Normal,
                        textAlign = TextAlign.Center,
                        lineHeight = 28.sp
                    )
                }

                // Bagian bawah dengan mascot dan background
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 36.dp)
                        .background(redColor),
                    contentAlignment = Alignment.BottomEnd
                ) {
                    Box(modifier = Modifier.size(300.dp)) {
                        Image(
                            painter = painterResource(id = R.drawable.rectangle_13),
                            contentDescription = "Background Maskot",
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Fit
                        )
                        Image(
                            painter = painterResource(id = R.drawable.asset1),
                            contentDescription = "Mascot",
                            modifier = Modifier
                                .size(300.dp)
                                .padding(start = 20.dp, top = 30.dp),
                            contentScale = ContentScale.Fit
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun AppDescPreview(){
    AppDescView(navController = rememberNavController())
}
