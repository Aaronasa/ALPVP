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
fun MenujuHomepage1View(
    navController: NavHostController
) {
    val customFontFamily = FontFamily(
        Font(R.font.jua)
    )

    val redColor = Color(0xFF991E3D)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(redColor) // Set background ke merah tua
    ) {
        Button(
            onClick = {
                navController.navigate("MenujuHomepage2View") // Navigasi ke Homepage2View
            },
            modifier = Modifier
                .fillMaxSize() // Tombol mengisi seluruh layar
                .padding(0.dp), // Tanpa padding
            shape = RoundedCornerShape(0.dp), // Tombol tanpa sudut melengkung
            contentPadding = PaddingValues(0.dp) // Tombol tanpa padding konten
        ) {
            Column(
                modifier = Modifier.fillMaxSize()
                    .background(redColor)
                ,
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                // Header
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            Color.White,
                            shape = RoundedCornerShape(bottomEnd = 16.dp, bottomStart = 16.dp)
                        )
                        .padding(16.dp, 50.dp, 16.dp, 26.dp)
                ) {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            "Destinasi Rasa",
                            fontSize = 36.sp,
                            fontFamily = customFontFamily,
                            fontWeight = FontWeight.SemiBold,
                            color = Color(0xFF0B1A3A)
                        )
                    }
                }

                // Konten utama
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 80.dp, start = 10.dp, end = 10.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Gambar Peta
                    Box(
                        modifier = Modifier
                            .size(200.dp)
                            .background(
                                color = Color.White,
                                shape = RoundedCornerShape(20.dp)
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.map),
                            contentDescription = "Map Icon",
                            modifier = Modifier
                                .size(200.dp)
                                .padding(top = 10.dp),
                            contentScale = ContentScale.Fit
                        )
                    }

                    // Teks deskripsi
                    Text(
                        text = "Rasalanka features a destination search that helps you discover the best local dishes and souvenirs in your travel city.",
                        color = Color.White,
                        fontSize = 18.sp,
                        fontFamily = customFontFamily,
                        fontWeight = FontWeight.Normal,
                        textAlign = TextAlign.Center,
                        lineHeight = 28.sp,
                        modifier = Modifier.padding(top = 80.dp)
                    )

                    // Gambar Page Control
                    Image(
                        painter = painterResource(id = R.drawable.pagecontrol1),
                        contentDescription = "pagecontrol1",
                        modifier = Modifier
                            .size(250.dp)
                            .padding(top = 20.dp)
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun MenujuHomepage1PreView() {
    MenujuHomepage1View(navController = rememberNavController())
}
