package com.example.project

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.project.ui.theme.ProjectTheme


@Composable
fun ChoosingMode(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(android.graphics.Color.parseColor("#f2f1f6")))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "你今天想...", fontSize = 40.sp)

        Button(
            onClick = { navController.navigate("chatting") },
            colors = ButtonDefaults.buttonColors(containerColor = ThemeColor),
            shape = CircleShape,
            modifier = Modifier
                .padding(vertical = 8.dp)
                .fillMaxWidth(0.55f)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.imagegallery),
                    contentDescription = null,
                    modifier = Modifier
                        .size(24.dp)
                )
                Column(
                    modifier = Modifier
                        .padding(start = 25.dp)
                ) {
                    Text(text = "上傳圖片")

                }
            }
        }
        Button(
            onClick = { navController.navigate("cards") },
            colors = ButtonDefaults.buttonColors(containerColor = ThemeColor),
            shape = CircleShape,
            modifier = Modifier
                .padding(vertical = 8.dp)
                .fillMaxWidth(0.55f)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.flashcards),
                    contentDescription = null,
                    modifier = Modifier
                        .size(24.dp)
                )
                Column(
                    modifier = Modifier
                        .padding(start = 25.dp)
                ) {
                    Text(text = "抽取卡片")

                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ChatScreenPreview() {
    val navController = rememberNavController()
    ProjectTheme {
        ChoosingMode(navController)
    }
}