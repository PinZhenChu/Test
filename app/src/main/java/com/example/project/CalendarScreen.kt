@file:Suppress("NAME_SHADOWING")

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
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter



@Composable
fun MonthSelectionScreen(navController: NavController,allConversationData : List<String>) {


    val currentDate = LocalDateTime.now()
    val currentMonth = "${currentDate.year}.${currentDate.monthValue.toString().padStart(2, '0')}"

    var selectedMonth by remember { mutableStateOf(currentMonth) }

    // 计算每个月的对话次数
    val conversationCountByMonth = allConversationData
        .map { LocalDateTime.parse(it, DateTimeFormatter.ofPattern("yyyy.MM.dd.HH:mm:ss")) }
        .groupBy { it.format(DateTimeFormatter.ofPattern("yyyy.MM")) }
        .mapValues { it.value.size }

    val monthFormatter = DateTimeFormatter.ofPattern("MMMM yyyy")
    val originalFormatter = DateTimeFormatter.ofPattern("yyyy.MM")
    val formattedMonth = try {
        val localDate = LocalDate.parse(selectedMonth, originalFormatter)
        localDate.format(monthFormatter)
    } catch (e: Exception) {
        "Invalid Date"
    }

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
            items(conversationCountByMonth.toList()) { (month, conversationCount) ->
                val formattedMonth = LocalDateTime.parse("$month.01.00:00:00", DateTimeFormatter.ofPattern("yyyy.MM.dd.HH:mm:ss"))
                    .format(DateTimeFormatter.ofPattern("yyyy年 M月"))
                MonthItem(
                    month = formattedMonth,
                    conversations = "$conversationCount 次對話",
                    isSelected = selectedMonth == month,
                    onClick = {
                        selectedMonth = month
                        navController.navigate("month/$selectedMonth")
                    }
                )
            }
        }
    }
}

//@Composable
//fun Newtest(selectedMonth: String) {
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(16.dp),
//        horizontalAlignment = Alignment.CenterHorizontally
//    ) {
//        Text(
//            text = selectedMonth,
//            fontSize = 18.sp,
//            modifier = Modifier.padding(16.dp)
//        )
//    }
//}

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
        Column {
            MonthSelectionScreen(navController,allConversationData = allConversationData)
        }
    }
}
