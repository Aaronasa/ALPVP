package com.example.foodalp.View

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.foodalp.R

@Composable
fun Launchview(
    navController : NavController? = null
) {

    Column {
        Box(modifier = Modifier.fillMaxHeight().background(color = Color.White)
        ) {
            Image(
                painter = painterResource(id = R.drawable.rasalanka),
                contentDescription = "Logo",
                modifier = Modifier.fillMaxSize().padding(end = 15.dp)
            )
            Image(
                painter = painterResource(id = R.drawable.group_8),
                contentDescription = "Background orange",
                modifier = Modifier.fillMaxSize()
            )
        }
    }

}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DefaultPreview3() {
    Launchview()
}