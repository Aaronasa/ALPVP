package com.example.foodalp.View

import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.foodalp.models.UpdateRestaurantRequest
import com.example.foodalp.viewmodel.RestaurantViewModel
import com.example.foodalp.R
import com.example.foodalp.models.RestaurantModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateRestaurantView(
    navController: NavHostController,
    restaurantId: Int,
    token: String,
    RestaurantViewModel: RestaurantViewModel = viewModel(),

) {
    Log.d("UpdateRestaurantView", "Token: $token")
    Log.d("UpdateRestaurantView", "Restaurant ID: $restaurantId")

    val Restaurant by RestaurantViewModel.RestaurantById.collectAsState()
    val UIstate by RestaurantViewModel.UIstate.collectAsState()


    // State for form fields
    var restaurantName by remember { mutableStateOf("") }
    var restaurantAddress by remember { mutableStateOf("") }
    var restaurantPhone by remember { mutableStateOf("") }
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    var isDataLoaded by remember { mutableStateOf(false) }

    val context = LocalContext.current
    val uiState by RestaurantViewModel.uiState.collectAsState()

    LaunchedEffect(restaurantId) {
        Log.d("UpdateRestaurantView", "Loading restaurant data for ID: $restaurantId")
        if (!isDataLoaded) {
            Log.d("UpdateRestaurantView", "Check load data => id: $restaurantId dan token: $token")
            RestaurantViewModel.FetchRestaurantByIdAdmin(
                token = token, // Replace with actual token logic
                restaurantId = restaurantId
            ) { restaurant ->
                Log.d("UpdateRestaurantView", "Loaded restaurant: $restaurant")
                restaurant?.let {
                    restaurantName = it.name
                    restaurantAddress = it.address
                    restaurantPhone = it.phone
                    isDataLoaded = true
                }
            }
        }
    }


    // Image picker launcher
    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri -> imageUri = uri }
    )

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.group_8),
            contentDescription = "Background orange",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Top Background
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
                Text(
                    "Update Restaurant",
                    fontSize = 36.sp,
                    color = Color.White
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Column(
                horizontalAlignment = Alignment.Start,
                modifier = Modifier
                    .padding(horizontal = 32.dp)
                    .fillMaxWidth()
            ) {
                TextField(
                    value = restaurantName,
                    onValueChange = { restaurantName = it },
                    label = { Text("Restaurant Name") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(16.dp))

                TextField(
                    value = restaurantAddress,
                    onValueChange = { restaurantAddress = it },
                    label = { Text("Address") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(16.dp))

                TextField(
                    value = restaurantPhone,
                    onValueChange = { restaurantPhone = it },
                    label = { Text("Phone Number") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Restaurant Image",
                    fontSize = 20.sp,
                    color = Color(0xFF0F0A3F),
                    modifier = Modifier.padding(bottom = 4.dp, start = 5.dp)
                )
                Button(
                    onClick = { imagePickerLauncher.launch("image/*") },
                    modifier = Modifier
                        .wrapContentWidth()
                        .height(36.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF9C254D))
                ) {
                    Text("Upload Image", color = Color.White)
                }

                imageUri?.let { uri ->
                    Spacer(modifier = Modifier.height(16.dp))
                    Image(
                        painter = rememberAsyncImagePainter(uri),
                        contentDescription = "Selected Image",
                        modifier = Modifier
                            .size(150.dp)
                            .clip(RoundedCornerShape(8.dp)),
                        contentScale = ContentScale.Crop
                    )
                }
            }

            Spacer(modifier = Modifier.height(100.dp))
            Button(
                onClick = {
                    val name = restaurantName.trim()
                    val address = restaurantAddress.trim()
                    val phone = restaurantPhone.trim()

                    if (name.isNotEmpty() && address.isNotEmpty() && phone.isNotEmpty()) {

                            try {
                                val imagePart = imageUri?.let { uri ->
                                    RestaurantViewModel.createImagePart(context, uri)
                                }

                                val request = UpdateRestaurantRequest(
                                    name = name,
                                    address = address,
                                    phone = phone,
                                    image = imagePart
                                )

                                RestaurantViewModel.updateRestaurant(token, restaurantId, request)
                                Toast.makeText(context, "Restaurant Updated Successfully", Toast.LENGTH_SHORT).show()
                                navController.popBackStack()
                            } catch (e: Exception) {
                                Toast.makeText(context, e.message ?: "Update failed", Toast.LENGTH_SHORT).show()
                            }
                    } else {
                        Toast.makeText(context, "Please fill all the fields!", Toast.LENGTH_SHORT).show()
                    }
                },
                modifier = Modifier.fillMaxWidth(0.6f),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF9C254D))
            ) {
                Text(
                    "Update",
                    color = Color.White,
                    fontSize = 20.sp
                )
            }

            if (uiState.isLoading) {
                CircularProgressIndicator()
            } else if (uiState.errorMessage != null) {
                Text(uiState.errorMessage!!, color = Color.Red)
            }
        }
    }
}




