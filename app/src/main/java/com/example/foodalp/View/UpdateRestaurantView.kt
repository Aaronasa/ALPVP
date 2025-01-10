package com.example.foodalp.View

import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateRestaurantView(
    navController: NavHostController,
    restaurantId: Int,
    token: String,
    restaurantViewModel: RestaurantViewModel = viewModel()
) {
    Log.d("UpdateRestaurantView", "Token: $token")
    Log.d("UpdateRestaurantView", "Restaurant ID: $restaurantId")

    val restaurant by restaurantViewModel.selectedRestaurant.collectAsState()
    val uiState by restaurantViewModel.uiState.collectAsState()

    // State for form fields
    var restaurantName by remember { mutableStateOf("") }
    var restaurantAddress by remember { mutableStateOf("") }
    var restaurantPhone by remember { mutableStateOf("") }
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    var isDataLoaded by remember { mutableStateOf(false) }

    val context = LocalContext.current

    // Load restaurant data when the component is first displayed
    LaunchedEffect(restaurantId) {
        if (!isDataLoaded) {
            val loadedRestaurant = restaurantViewModel.fetchRestaurantById(token, restaurantId)
            loadedRestaurant?.let {
                restaurantName = it.name
                restaurantAddress = it.address
                restaurantPhone = it.phone
                isDataLoaded = true
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

            // Form Fields
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
                        val imagePart = imageUri?.let { uri ->
                            restaurantViewModel.createImagePart(
                                context,
                                uri
                            )
                        }

                        val request = UpdateRestaurantRequest(
                            id = restaurantId,
                            name = name,
                            address = address,
                            phone = phone,
                            image = imagePart
                        )

                        restaurantViewModel.updateRestaurant(
                            request,
                            token = token
                        )
                        Toast.makeText(
                            context,
                            "Restaurant Updated Successfully",
                            Toast.LENGTH_SHORT
                        ).show()
                        navController.popBackStack()
                    } else {
                        Toast.makeText(context, "Please fill all the fields!", Toast.LENGTH_SHORT)
                            .show()
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
