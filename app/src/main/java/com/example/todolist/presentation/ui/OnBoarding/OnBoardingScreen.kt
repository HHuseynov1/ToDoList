package com.example.todolist.presentation.ui.OnBoarding

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.todolist.R
import com.example.todolist.ui.theme.GrayToDo
import com.example.todolist.ui.theme.interFont

val onBoardingList = OnBoarding(
    R.drawable.on_board_image,
    "",
    listOf(
        "Exercise",
        "Read books",
        "Meditate",
        "Plan meals",
        "Water plants",
        "Journal",
        "Stretch for 15 minutes",
        "Review goals before"
    )
)

@Composable
fun OnBoardingScreen(onFinished: () -> Unit) {

    var onBoardingData by remember { mutableStateOf(onBoardingList) }

    Surface {
            Box(
                Modifier
                    .fillMaxSize()
                    .background(Color.White)
            ) {

                Image(
                    painter = painterResource(onBoardingData.image),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )

                Column(
                    modifier = Modifier.fillMaxSize().padding(bottom = 15.dp),
                    verticalArrangement = Arrangement.Bottom

                ) {
                    Text(
                        "Pick some new habits to get started",
                        fontFamily = interFont,
                        fontSize = 36.sp,
                        color = Color.Black,
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                    )

                    Spacer(Modifier.size(200.dp))

                    Image(
                        painter = painterResource(R.drawable.cards),
                        contentDescription = null,
                        Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)
                    )

                    Spacer(Modifier.size(25.dp))

                    Button(
                        onClick = { onFinished() },
                        Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),

                        colors = ButtonDefaults.buttonColors(containerColor = GrayToDo),
                        shape = RoundedCornerShape(12.dp),

                        ) {
                        Text("Continue", modifier = Modifier.padding(vertical = 8.dp))
                    }
                }

        }
            // }
        }
    }

    @Preview(showBackground = true)
    @Composable
    fun OnBoardingScreenPreview() {
        OnBoardingScreen {}
    }