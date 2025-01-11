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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import com.example.foodalp.R
import com.example.foodalp.models.CreateCityRequest
import com.example.foodalp.viewmodel.CityViewModel
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddCityView(
    navController: androidx.navigation.NavHostController,
    viewModel: CityViewModel = viewModel()
) {
    var cityName by remember { mutableStateOf("") }
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    val token = getUserToken()
    val uiState by viewModel.uiState.collectAsState()

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
            // Header
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
                    "Add City",
                    fontSize = 36.sp,
                    color = Color.White
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Input Fields
            Column(
                horizontalAlignment = Alignment.Start,
                modifier = Modifier
                    .padding(horizontal = 32.dp)
                    .fillMaxWidth()
            ) {
                TextField(
                    value = cityName,
                    onValueChange = { cityName = it },
                    label = { Text("City Name") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "City Image",
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

            // Submit Button
            val context = LocalContext.current
            Button(
                onClick = {
                    val name = cityName.trim()
                    val imageUri = imageUri

                    if (name.isNotEmpty() && imageUri != null) {
                        val imagePart = createImagePart1(context, imageUri)


                        val request = CreateCityRequest(
                            name = name,
                            image = imagePart
                        )

                        if (token != null) {
                            viewModel.createCity(token, context, request)
                        }
                        Toast.makeText(
                            navController.context,
                            "City Created Successfully",
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

fun createImagePart1(context: Context, imageUri: Uri): MultipartBody.Part {
    val file = File(getRealPathFromURI1(context, imageUri))
    val requestBody = file.asRequestBody("image/jpeg".toMediaTypeOrNull())
    return MultipartBody.Part.createFormData("image", file.name, requestBody)
}

fun getRealPathFromURI1(context: Context, uri: Uri): String? {
    var filePath: String? = null

    if (uri.scheme == "content") {
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

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun AddCityPreView() {
    AddCityView(navController = rememberNavController())
}
