package com.example.foodalp.View

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun UserCardView() {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RectangleShape,
        border = BorderStroke(1.dp, Color.Gray) // Adding border to the card
    ) {
        Column(modifier = Modifier.padding(10.dp)) {
            // User Info - Username and Email displayed vertically
            Column(modifier = Modifier.padding(10.dp, 0.dp, 15.dp, 0.dp)) {
                Text(
                    text = "nama", // Username
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    modifier = Modifier.padding(bottom = 3.dp)
                )
                Text(
                    text = "email", // Email
                    fontSize = 13.sp,
                    color = Color.Gray
                )
            }

            // Buttons Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Update Button
                Button(
                    onClick = { /* Handle Update */ },
                    modifier = Modifier.padding(4.dp)
                ) {
                    Text("Update")
                }

                // Delete Button
                Button(
                    onClick = { /* Handle Delete */ },
                    modifier = Modifier
                        .padding(4.dp)

                ) {
                    Text("Delete")
                }
            }
        }
    }
}

@Preview()
@Composable
fun UserCardPreview() {
    UserCardView()
}
