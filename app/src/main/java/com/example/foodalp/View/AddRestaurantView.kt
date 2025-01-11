package com.example.foodalp.View

import android.content.Context
import android.net.Uri
import android.provider.MediaStore
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import com.example.foodalp.R
import com.example.foodalp.models.CreateRestaurantRequest
import com.example.foodalp.viewmodel.RestaurantViewModel
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddRestaurantView(
    navController: NavHostController,
    role: Int,
    viewModel: RestaurantViewModel = viewModel()
) {
    var restaurantName by remember { mutableStateOf("") }
    var restaurantAddress by remember { mutableStateOf("") }
    var restaurantPhone by remember { mutableStateOf("") }
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    val token = getUserToken()

    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri -> imageUri = uri }
    )

    val uiState by viewModel.uiState.collectAsState()

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
                Text(
                    "Add Restaurant",
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
            val context = LocalContext.current
            Button(
                onClick = {
                    val name = restaurantName.trim()
                    val address = restaurantAddress.trim()
                    val phone = restaurantPhone.trim()
                    val imageUri = imageUri // Uri object, not string

                    // Validate input
                    if (name.isNotEmpty() && address.isNotEmpty() && phone.isNotEmpty() && imageUri != null) {
                        val imagePart = createImagePart(context, imageUri) // Convert Uri to MultipartBody.Part

                        val request = CreateRestaurantRequest(
                            name = name,
                            address = address,
                            phone = phone,
                            image = imagePart // Pass MultipartBody.Part
                        )

                        if (token != null) {
                            if(role == 1){
                                viewModel.createRestaurantAdmin(token, context, request)
                            }else if (role == 2) {
                                viewModel.createRestaurant(token, context, request)
                            }
                        }
                        Toast.makeText(
                            navController.context,
                            "Restaurant Created Successfully",
                            Toast.LENGTH_SHORT
                        ).show()
                        navController.popBackStack()
                    } else {
                        Toast.makeText(navController.context, "Please fill all the fields!", Toast.LENGTH_SHORT).show()
                    }
                },
                modifier = Modifier.fillMaxWidth(0.6f),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF9C254D))
            ) {
                Text(
                    "Create",
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

fun createImagePart(context: Context, imageUri: Uri): MultipartBody.Part {
    val file = File(getRealPathFromURI(context, imageUri)) // Convert URI to actual file
    val requestBody = file.asRequestBody("image/jpeg".toMediaTypeOrNull()) // Adjust content type accordingly
    return MultipartBody.Part.createFormData("image", file.name, requestBody)
}

fun getRealPathFromURI(context: Context, uri: Uri): String? {
    var filePath: String? = null

    // Check if the URI is a content URI
    if (uri.scheme == "content") {
        // Query the content resolver for the file path
        val proj = arrayOf(MediaStore.Images.Media.DATA)
        val cursor = context.contentResolver.query(uri, proj, null, null, null)

        if (cursor != null) {
            val columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
            cursor.moveToFirst()
            filePath = cursor.getString(columnIndex)
            cursor.close()
        }
    } else if (uri.scheme == "file") {
        filePath = uri.path
    }

    return filePath
}

