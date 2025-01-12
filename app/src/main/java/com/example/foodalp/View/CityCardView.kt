package com.example.foodalp.View

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.SubcomposeAsyncImage
import com.example.foodalp.R
import com.example.foodalp.enums.ListScreen
import com.example.foodalp.models.CityModel
import com.example.foodalp.viewmodel.CityViewModel

@Composable
fun CityCard(
    modifier: Modifier = Modifier,
    city: CityModel,
    viewModel: CityViewModel,
    username: String,
    role: Int,
    userId: Int,
    navController: NavController
) {
    val customFontFamily = FontFamily(
        Font(R.font.jua)
    )

    val token = getUserToken()
    val cityId = city.id

    // Card Composable
    Card(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
        onClick = {
//            navController.navigate(ListScreen.CityDetailView.name + "/${city.id}/${token}")
            navController.navigate(ListScreen.CityDetailView.name + "/${cityId}/${token}/${username}/${userId}/${role}")
        },

        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
            .size(width = 240.dp, height = 100.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(12.dp))
        ) {
            // Background Image
            SubcomposeAsyncImage(
                model = city.image,
                contentDescription = "Restaurant Image",
                loading = {
                    CircularProgressIndicator() // Show a loading indicator while the image loads
                },
                error = {
                    Text("Image failed to load") // Fallback UI if the image fails to load
                },
                contentScale = ContentScale.Crop, // Optional: Adjust the scaling of the image
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clip(RoundedCornerShape(8.dp)) // Optional: Apply rounded corners
            )

            // City Name Overlay
            Text(
                text = city.name ?: "Unknown City",
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(16.dp),
                textAlign = TextAlign.Center,
                style = TextStyle(
                    color = Color.White,
                    fontFamily = customFontFamily,
                    fontSize = 30.sp,
                    shadow = Shadow(
                        color = Color.Black,
                        blurRadius = 2f,
                        offset = Offset(1f, 1f)
                    )
                )
            )
        }
    }
}