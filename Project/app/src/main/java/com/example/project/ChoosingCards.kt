package com.example.project

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.AlertDialog
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import com.example.project.ui.theme.ProjectTheme

@Composable
fun ChoosingCards() {
    var showDialog by remember { mutableStateOf(false) }
    var selectedCard by remember { mutableStateOf(-1) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(android.graphics.Color.parseColor("#f2f1f6")))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "請抽卡", fontSize = 40.sp, fontWeight = FontWeight.SemiBold)

        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            modifier = Modifier
                .padding(top = 16.dp)

        ) {
            items(12) { index ->
                Box(
                    modifier = Modifier
//                        .size(100.dp)
//                        .height(100.dp)
//                        .width(50.dp)
                        .aspectRatio(3 / 4f)
                        .padding(8.dp)
                        .background(Color.Gray)
                        .clickable {
                            selectedCard = index
                            showDialog = true
                        }
                )
            }
        }
    }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            buttons = {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(color = ThemeColor)
//                        .background(color = Color(android.graphics.Color.parseColor("#e7dece")))
                        .padding(all = 16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,

                    ) {
                    Text(
                        text = "是否確定要抽取這張卡片？",
                        style = MaterialTheme.typography.h6,
                        modifier = Modifier.padding(bottom = 16.dp),
                        fontWeight = FontWeight.SemiBold,
                        color = Color.White
                    )
                    Text(
                        text = "選擇卡片 $selectedCard",
                        style = MaterialTheme.typography.body1,
                        modifier = Modifier.padding(bottom = 16.dp),
                        color = Color.White,
                        fontWeight = FontWeight.SemiBold
                    )

                    Row(
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Button(
                            onClick = { showDialog = false /* 確認邏輯處理 */ },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(android.graphics.Color.parseColor("#e7dece")),
//                                containerColor = Color(0xFFD5BFA0),
                                contentColor = Color.White
                            ),
                            modifier = Modifier
//                                .fillMaxWidth(0.3f)
                        ) {
                            Text("是", fontWeight = FontWeight.Bold)
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Button(
                            onClick = { showDialog = false },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(android.graphics.Color.parseColor("#e7dece")),
//                                containerColor = Color(0xFFD5BFA0),
                                contentColor = Color.White
                            ),
                            modifier = Modifier
//                                .fillMaxWidth(0.3f)
                        ) {
                            Text("否", fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        )
    }

}

@Preview(showBackground = true)
@Composable
fun ChoosingCardsPreview() {
    val navController = rememberNavController()
    ProjectTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = androidx.compose.material3.MaterialTheme.colorScheme.background
        ) {
            ChoosingCards()
        }
    }
}