package com.example.project

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.project.ui.theme.ProjectTheme

@Composable
fun MonthSelectionScreen(navController: NavController) {
    val months = listOf(
        "2024年 5月" to "12次對話",
        "2024年 4月" to "19次對話",
        "2024年 3月" to "15次對話",
        "2024年 2月" to "10次對話",
        "2024年 1月" to "2次對話",
        "2023年 12月" to "5次對話",
        "2023年 11月" to "13次對話",
        "2023年 10月" to "10次對話",
        "2023年 9月" to "12次對話",
        "2023年 8月" to "9次對話",
        "2023年 7月" to "7次對話",
        "2023年 6月" to "1次對話",
        "2023年 4月" to "10次對話",
        "2023年 2月" to "12次對話",
        "2022年 12月" to "9次對話",
        "2022年 7月" to "7次對話",
        "2022年 6月" to "1次對話"
    )
    var selectedMonth by remember { mutableStateOf("") }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "2022年5月 是你來到___的第一個月，你想要回顧哪個月的紀錄呢？",
            fontSize = 18.sp,
            modifier = Modifier.padding(16.dp)
        )
        LazyColumn {
            items(months) { (month, conversations) ->
                MonthItem(
                    month = month,
                    conversations = conversations,
                    isSelected = selectedMonth == month,
                    onClick = { selectedMonth = month }
                )
            }
        }
    }
}

@Composable
fun MonthItem(
    month: String,
    conversations: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val backgroundColor = if (isSelected) Color.LightGray else LightColor

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp, horizontal = 16.dp)
            .background(backgroundColor, RoundedCornerShape(10.dp))
            .clickable { onClick() }
//            .border(BorderStroke(0.05.dp, Color.Black),RoundedCornerShape(10.dp))
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = month, fontSize = 16.sp)
        Text(text = conversations, fontSize = 16.sp, fontWeight = FontWeight.Bold)
    }
}

@Preview(showBackground = true)
@Composable
fun MonthSelectionScreenPreview() {
    val navController = rememberNavController()
    ProjectTheme {
        MonthSelectionScreen(navController)
    }
}