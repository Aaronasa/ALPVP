package com.example.foodalp.View

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.foodalp.R

@Composable
fun ReviewCard() {
    val customFontFamily = FontFamily(
        Font(R.font.jua)
    )
    val a = 5;

    Box(
        modifier = Modifier
            .padding(16.dp) // Outer padding to separate from screen edges
            .clip(RoundedCornerShape(16.dp)) // Rounded corners for the card
            .background(Color.Gray) // Background color of the card
            .padding(8.dp) // Padding inside the card for a clean layout
    ) {
        Column (
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ){
            Row (verticalAlignment = Alignment.CenterVertically){
                Image(
                    painter = painterResource(id = R.drawable.baseline_person_24),
                    contentDescription = "Profile Picture",
                    modifier = Modifier
                        .size(40.dp)
                        .clip(RoundedCornerShape(50))

                    )
                Text("Rinaldy",
                    color = Color.Black,
                    fontFamily = customFontFamily,
                    modifier = Modifier.padding(start = 8.dp, top = 2.dp),
                    fontSize = 30.sp
                    )

                Row(modifier = Modifier.padding(start = 8.dp)) {

                    if (a == 1){
                        Icon(painter = painterResource(id = R.drawable.baseline_star_24),
                            contentDescription = "star Icon",
                            tint = Color.Yellow,
                            modifier = Modifier.size(28.dp)
                        )
                        Icon(painter = painterResource(id = R.drawable.baseline_star_border_24),
                            contentDescription = "star Icon",
                            tint = Color.Yellow,
                            modifier = Modifier.size(28.dp)
                        )
                        Icon(painter = painterResource(id = R.drawable.baseline_star_border_24),
                            contentDescription = "star Icon",
                            tint = Color.Yellow,
                            modifier = Modifier.size(28.dp)
                        )
                        Icon(painter = painterResource(id = R.drawable.baseline_star_border_24),
                            contentDescription = "star Icon",
                            tint = Color.Yellow,
                            modifier = Modifier.size(28.dp)
                        )
                        Icon(painter = painterResource(id = R.drawable.baseline_star_border_24),
                            contentDescription = "star Icon",
                            tint = Color.Yellow,
                            modifier = Modifier.size(28.dp)
                        )
                    }
                    else if (a == 2){
                        Icon(painter = painterResource(id = R.drawable.baseline_star_24),
                            contentDescription = "star Icon",
                            tint = Color.Yellow,
                            modifier = Modifier.size(28.dp)
                        )
                        Icon(painter = painterResource(id = R.drawable.baseline_star_24),
                            contentDescription = "star Icon",
                            tint = Color.Yellow,
                            modifier = Modifier.size(28.dp)
                        )
                        Icon(painter = painterResource(id = R.drawable.baseline_star_border_24),
                            contentDescription = "star Icon",
                            tint = Color.Yellow,
                            modifier = Modifier.size(28.dp)
                        )
                        Icon(painter = painterResource(id = R.drawable.baseline_star_border_24),
                            contentDescription = "star Icon",
                            tint = Color.Yellow,
                            modifier = Modifier.size(28.dp)
                        )
                        Icon(painter = painterResource(id = R.drawable.baseline_star_border_24),
                            contentDescription = "star Icon",
                            tint = Color.Yellow,
                            modifier = Modifier.size(28.dp)
                        )
                    }
                    else if (a == 3){
                        Icon(painter = painterResource(id = R.drawable.baseline_star_24),
                            contentDescription = "star Icon",
                            tint = Color.Yellow,
                            modifier = Modifier.size(28.dp)
                        )
                        Icon(painter = painterResource(id = R.drawable.baseline_star_24),
                            contentDescription = "star Icon",
                            tint = Color.Yellow,
                            modifier = Modifier.size(28.dp)
                        )
                        Icon(painter = painterResource(id = R.drawable.baseline_star_24),
                            contentDescription = "star Icon",
                            tint = Color.Yellow,
                            modifier = Modifier.size(28.dp)
                        )
                        Icon(painter = painterResource(id = R.drawable.baseline_star_border_24),
                            contentDescription = "star Icon",
                            tint = Color.Yellow,
                            modifier = Modifier.size(28.dp)
                        )
                        Icon(painter = painterResource(id = R.drawable.baseline_star_border_24),
                            contentDescription = "star Icon",
                            tint = Color.Yellow,
                            modifier = Modifier.size(28.dp)
                        )
                    }
                    else if (a == 4){
                        Icon(painter = painterResource(id = R.drawable.baseline_star_24),
                            contentDescription = "star Icon",
                            tint = Color.Yellow,
                            modifier = Modifier.size(28.dp)
                        )
                        Icon(painter = painterResource(id = R.drawable.baseline_star_24),
                            contentDescription = "star Icon",
                            tint = Color.Yellow,
                            modifier = Modifier.size(28.dp)
                        )
                        Icon(painter = painterResource(id = R.drawable.baseline_star_24),
                            contentDescription = "star Icon",
                            tint = Color.Yellow,
                            modifier = Modifier.size(28.dp)
                        )
                        Icon(painter = painterResource(id = R.drawable.baseline_star_24),
                            contentDescription = "star Icon",
                            tint = Color.Yellow,
                            modifier = Modifier.size(28.dp)
                        )
                        Icon(painter = painterResource(id = R.drawable.baseline_star_border_24),
                            contentDescription = "star Icon",
                            tint = Color.Yellow,
                            modifier = Modifier.size(28.dp)
                        )
                    }
                    else if (a == 5){
                        Icon(painter = painterResource(id = R.drawable.baseline_star_24),
                            contentDescription = "star Icon",
                            tint = Color.Yellow,
                            modifier = Modifier.size(28.dp)
                        )
                        Icon(painter = painterResource(id = R.drawable.baseline_star_24),
                            contentDescription = "star Icon",
                            tint = Color.Yellow,
                            modifier = Modifier.size(28.dp)
                        )
                        Icon(painter = painterResource(id = R.drawable.baseline_star_24),
                            contentDescription = "star Icon",
                            tint = Color.Yellow,
                            modifier = Modifier.size(28.dp)
                        )
                        Icon(painter = painterResource(id = R.drawable.baseline_star_24),
                            contentDescription = "star Icon",
                            tint = Color.Yellow,
                            modifier = Modifier.size(28.dp)
                        )
                        Icon(painter = painterResource(id = R.drawable.baseline_star_24),
                            contentDescription = "star Icon",
                            tint = Color.Yellow,
                            modifier = Modifier.size(28.dp)
                        )
                    }
                }


            }
            Row (
//                modifier = Modifier.padding(start = 40.dp)
            ){
                Text("This is a review",
                    color = Color.Black,
                    fontFamily = customFontFamily,
                    modifier = Modifier.padding(start = 8.dp, top = 2.dp),
                    fontSize = 30.sp
                )
            }
//            Row (
//                verticalAlignment = Alignment.CenterVertically,
//                modifier = Modifier.padding(start = 45.dp)
//            ){
//                Icon(painter = painterResource(id = R.drawable.baseline_star_24),
//                    contentDescription = "star Icon",
//                    tint = Color.Yellow,
//                    modifier = Modifier.size(28.dp)
//                )
//                Text("= Rate",
//                    color = Color.Black,
//                    fontFamily = customFontFamily,
//                    modifier = Modifier.padding(start = 4.dp, top = 2.dp),
//                    fontSize = 26.sp)
//                Text("= 4",
//                    color = Color.Black,
//                    fontFamily = customFontFamily,
//                    modifier = Modifier.padding(start = 4.dp, top = 2.dp),
//                    fontSize = 26.sp)
//            }
        }
    }
}

@Preview
@Composable
fun PreviewReviewCard() {
    ReviewCard()
}