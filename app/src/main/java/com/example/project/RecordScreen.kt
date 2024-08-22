package com.example.project

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.project.ui.theme.ProjectTheme
import com.patrykandpatrick.vico.compose.axis.horizontal.rememberBottomAxis
import com.patrykandpatrick.vico.compose.axis.vertical.rememberStartAxis
import com.patrykandpatrick.vico.compose.chart.Chart
import com.patrykandpatrick.vico.compose.chart.line.lineChart
import com.patrykandpatrick.vico.core.entry.entryModelOf
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun RecordScreen(navController: NavController, date: LocalDate?) {
    // Debugging output
    println("Received date: ${date?.format(DateTimeFormatter.ISO_LOCAL_DATE)}")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(ThemeColor)
            .padding(16.dp)
    ) {
        HeaderSection(navController,date)
        ContentSection()
        EmotionChart()
        ConversationSection()
    }
}

@Composable
fun HeaderSection(navController: NavController,date: LocalDate?) {
    Row(
        modifier = Modifier
            .padding(bottom = 8.dp, end = 10.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment =  Alignment.CenterVertically
    ) {
        IconButton(onClick = { navController.navigateUp() }) {
            Icon(
                bitmap = ImageBitmap.imageResource(id = R.drawable.back),
                contentDescription = null,
                modifier = Modifier.size(26.dp),
//                tint = Color.White // Set the icon color to white
            )
        }
        val formattedDate = date?.format(DateTimeFormatter.ofPattern("MMMM d yyyy")) ?: "No Date"
        Text(
            text = formattedDate,
            fontSize = 20.sp,
//            color = Color.White,
            modifier = Modifier.padding(4.dp)
        )
    }
}

@Composable
fun ContentSection() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White, RoundedCornerShape(8.dp))
            .padding(16.dp)
    ) {
        Text(
            text = "內容概述",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.align(Alignment.CenterHorizontally),
            fontSize = 20.sp
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "今天OO跟我分享了他家的貓咪小花最近的行為變化。他提到小花最近特別愛搞亂，總是在他的床上亂挖，每天早上都得整理一遍。他還提到小花變得更親人，總是黏著他。我建議他給小花準備一個專屬的小床，看她會不會喜歡。小花也確實很喜歡小明，常常在他身邊撒嬌。",
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Light
        )
    }
}

@Composable
fun EmotionChart() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp)
            .background(Color.White, RoundedCornerShape(8.dp))
            .padding(16.dp)
    ) {
        Text(
            text = "情緒變化",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.align(Alignment.CenterHorizontally),
            fontSize = 20.sp
        )
        Spacer(modifier = Modifier.height(8.dp))
        val chartEntryModel = entryModelOf(4f, 12f, 8f, 16f)
        Chart(
            chart = lineChart(),
            model = chartEntryModel,
            startAxis = rememberStartAxis(),
            bottomAxis = rememberBottomAxis(),
        )
    }
}

@Composable
fun ConversationSection() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp)
            .background(Color.White, RoundedCornerShape(8.dp))
            .padding(16.dp)
    ) {
        ConversationBubble("AI", "這是AI的回應")
        ConversationBubble("你", "這是你的回應")
        ConversationBubble("AI", "這是AI的回應")
        ConversationBubble("你", "這是你的回應")
        ConversationBubble("AI", "這是AI的回應")
        ConversationBubble("你", "這是你的回應")
        ConversationBubble("AI", "這是AI的回應")
    }
}

@Composable
fun ConversationBubble(sender: String, message: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = if (sender == "AI") Arrangement.Start else Arrangement.End
    ) {
        Box(
            modifier = Modifier
                .background(
                    if (sender == "AI") LightColor else ThemeColor,
                    RoundedCornerShape(8.dp)
                )
                .padding(8.dp)
        ) {
            Text(text = message, style = MaterialTheme.typography.bodyMedium)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RecordScreenPreview() {
    val navController = rememberNavController()
    ProjectTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            RecordScreen(
                navController = navController,
                date = LocalDate.of(2024, 8, 17) // Example date
            )
        }
    }
}
