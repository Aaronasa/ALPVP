package com.example.foodalp.View

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.foodalp.R

@Composable
fun MenujuHomepage3View(
    navController: NavHostController
){
    val customFontFamily = FontFamily(
        Font(R.font.jua)
    )
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0xFF991E3D))

    ) {
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        Color.White,
                        shape = RoundedCornerShape(bottomEnd = 16.dp, bottomStart = 16.dp)
                    )
                    .padding(16.dp, 50.dp, 16.dp, 26.dp),

                ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("Pilihan Rasa",
                        fontSize = 36.sp,
                        fontFamily = customFontFamily,
                        fontWeight = FontWeight.SemiBold
                    )
                }

            }

            Column(modifier = Modifier.fillMaxWidth()
                .padding(top = 40.dp,start = 10.dp, end = 10.dp),
                horizontalAlignment = Alignment.CenterHorizontally) {
                Box(
                    modifier = Modifier
                        .size(300.dp)
                        .background(
                            color = Color.Transparent,
                            shape = RoundedCornerShape(20.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Box(
                        modifier = Modifier
                            .size(200.dp)
                            .background(
                                color = Color.White,
                                shape = RoundedCornerShape(20.dp)
                            ),
                        contentAlignment = Alignment.Center
                    ) {

                    }
                    Image(
                        painter = painterResource(id = R.drawable.temurasa),
                        contentDescription = "Map Icon",
                        modifier = Modifier.size(300.dp)
                            .padding(top = 10.dp),
                        contentScale = ContentScale.Fit
                    )
                }

                Text(
                    text = "This feature allows users to view a complete list of restaurants in a specific city. Users can compare prices and reviews for each restaurant. With this feature, they can choose the restaurant that best fits their preferences based on food quality, pricing, and customer experiences.",
                    color = Color.White,
                    fontSize = 16.sp,
                    fontFamily = customFontFamily,
                    fontWeight = FontWeight.Normal,
                    textAlign = TextAlign.Center,
                    lineHeight = 28.sp,
                    modifier = Modifier.padding(top = 40.dp)

                )

                Image(painter = painterResource(id = R.drawable.pagecontrol3),
                    contentDescription = "pagecontrol3",
                    modifier = Modifier.size(250.dp)
                        .padding(top = 20.dp)
                )
            }
        }

    }

}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun MenujuHomepage3PreView() {
    MenujuHomepage3View(navController = rememberNavController())
}
