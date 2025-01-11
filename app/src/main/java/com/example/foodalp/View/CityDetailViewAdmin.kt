package com.example.foodalp.View

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.SubcomposeAsyncImage
import coil.compose.rememberAsyncImagePainter
import com.example.foodalp.R
import com.example.foodalp.uistates.CityState
import com.example.foodalp.viewmodel.CityViewModel
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CityDetailViewAdmin(
    cityId: Int,
    token: String,
    navController: NavController? = null,
    cityViewModel: CityViewModel = viewModel()
) {
    // State for selected image URI
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }

    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri ->
            selectedImageUri = uri
            Log.d("CityDetailViewAdmin", "Image selected: $uri")
        }
    )

    // State initialization
    var isCityDataLoaded by remember { mutableStateOf(false) }
    var cityName by remember { mutableStateOf("") }
    var cityImage by remember { mutableStateOf("") }
    var cityName1 by remember { mutableStateOf("") }
    var imageUri by remember { mutableStateOf<Uri?>(null) }

    // Observing city state
    val cityState by cityViewModel.cityState.observeAsState(CityState.Loading)

    // LaunchedEffect for loading city details
    LaunchedEffect(cityId) {
        if (!isCityDataLoaded) {
            cityViewModel.FetchCityById(token, cityId) { city ->
                city?.let {
                    cityName = it.name
                    cityImage = it.image
                    cityName1 = it.name
                    isCityDataLoaded = true
                } ?: Log.e("CityDetailViewAdmin", "Failed to load city details")
            }
        }
    }

    // Custom font family
    val customFontFamily = FontFamily(Font(R.font.jua))

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Box {
            // Background Image
            Image(
                painter = painterResource(id = R.drawable.group_8),
                contentDescription = "Background orange",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )

            // Text Overlay
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                contentAlignment = Alignment.TopStart
            ) {
                Column {
                    Text(
                        text = "Name",
                        color = Color.Gray,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = customFontFamily,
                        modifier = Modifier.padding(start = 20.dp, top = 20.dp)
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = cityName,
                        color = Color(0xFF0C094E),
                        fontSize = 30.sp,
                        fontFamily = customFontFamily,
                        fontWeight = FontWeight.Normal,
                        modifier = Modifier.padding(start = 15.dp)
                    )

                    SubcomposeAsyncImage(
                        model = cityImage,
                        contentDescription = "City Image",
                        loading = {
                            CircularProgressIndicator()
                        },
                        error = {
                            Text("Image failed to load")
                        },
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                            .clip(RoundedCornerShape(8.dp))
                    )

                    TextField(
                        value = cityName1,
                        onValueChange = { cityName1 = it },
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
                            unfocusedIndicatorColor = Color.Transparent
                        ),
                        textStyle = androidx.compose.ui.text.TextStyle(
                            fontSize = 20.sp
                        ),
                        singleLine = true
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

                    val context = LocalContext.current

                    Button(
                        onClick = {
                            selectedImageUri?.let { uri ->
                                val imagePart = createImagePart2(context, uri)
                                if (imagePart != null) {
                                    cityViewModel.updateCity(
                                        token = token,
                                        id = cityId,
                                        name = cityName1,
                                        context = context,
                                        imageUri = uri)
                                } else {
                                    Log.e("CityDetailViewAdmin", "Failed to create image part")
                                }
                            } ?: Log.e("CityDetailViewAdmin", "No image selected")
                        },
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

                    Button(
                        onClick = {
                            cityViewModel.deleteCity(
                                token = token,
                                id = cityId,
                                onSuccess = {
                                    Log.d("CityDetailViewAdmin", "City deleted successfully")
                                    navController?.navigate("AdminPage") {
                                        popUpTo("CityDetailViewAdmin") { inclusive = true }
                                    }
                                },
                                onError = { errorMessage ->
                                    Log.e("CityDetailViewAdmin", "Failed to delete city: $errorMessage")
                                }
                            )
                        },
                        modifier = Modifier
                            .fillMaxWidth(0.6f),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF9C254D))
                    ) {
                        Text(
                            "Delete",
                            color = Color.White,
                            fontFamily = customFontFamily,
                            fontSize = 20.sp
                        )
                    }
                }
            }
        }
    }
}

/**
 * Create a MultipartBody.Part from a given URI.
 */

fun getRealPathFromURI2(context: Context, uri: Uri): String? {
    val cursor = context.contentResolver.query(uri, null, null, null, null)
    return if (cursor != null && cursor.moveToFirst()) {
        val index = cursor.getColumnIndex("_data")
        val path = if (index != -1) cursor.getString(index) else null
        cursor.close()
        path
    } else {
        null
    }
}

fun createImagePart2(context: Context, imageUri: Uri): MultipartBody.Part? {
    val realPath = getRealPathFromURI2(context, imageUri) ?: return null
    val file = File(realPath)
    if (!file.exists()) return null

    val requestBody = file.asRequestBody("image/jpeg".toMediaTypeOrNull())
    return MultipartBody.Part.createFormData("image", file.name, requestBody)
}
