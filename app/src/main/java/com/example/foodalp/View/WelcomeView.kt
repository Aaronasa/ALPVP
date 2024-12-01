package com.example.foodalp.View

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.foodalp.R
import com.example.foodalp.Route.ListScreen

val customFontFamily = FontFamily(
    Font(R.font.jua)
)

@Composable
fun Welcomeview(
    navController: NavHostController
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 80.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Image(
                painter = painterResource(id = R.drawable.rasalanka),
                contentDescription = "Logo",
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
                    .padding(end = 16.dp)
            )

            Text(
                text = "Welcome to Rasalanka",
                fontSize = 20.sp,
                color = Color(0xFF991E3D),
                fontFamily = customFontFamily,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 24.dp)
            )

            Button(
                onClick = {
                    navController.navigate(ListScreen.Registerview.name)
                },
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .padding(bottom = 8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF991E3D),
                    contentColor = Color(0xFFFFFFFF)
                ),
                shape = RoundedCornerShape(10.dp)
            ) {
                Text(
                    text = "Register",
                    fontSize = 22.sp,
                    color = Color(0xFFFFFFFF),
                    fontFamily = customFontFamily
                )
            }

            Button(
                onClick = {
                    navController.navigate(ListScreen.Loginview.name)
                },
                modifier = Modifier
                    .fillMaxWidth(0.8f),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF931D3B).copy(alpha = 0.2f),
                    contentColor = Color(0xFF0C094E)
                ),
                shape = RoundedCornerShape(10.dp)
            ) {
                Text(
                    text = "LogIn",
                    fontSize = 22.sp,
                    fontFamily = customFontFamily
                )
            }

        }

        Image(
            painter = painterResource(id = R.drawable.group_8),
            contentDescription = "Background orange",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DefaultPreview1() {
    Welcomeview(navController = rememberNavController())
}
