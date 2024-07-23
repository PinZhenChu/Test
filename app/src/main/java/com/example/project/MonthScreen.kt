package com.example.project

import android.graphics.Paint
import android.graphics.PointF
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.project.ui.theme.ProjectTheme
import java.time.LocalDate
import kotlin.random.Random


@Composable
fun MonthScreen(navController: NavController) {
    var showContent by remember { mutableStateOf(true) }
    var isMonthSelected by remember { mutableStateOf(true) }
    var selectedDate by remember { mutableStateOf<LocalDate?>(null) }
    val daysWithData = setOf(1, 7, 11, 18, 22, 23, 25, 30, 31)

    Surface(color = MaterialTheme.colorScheme.surface) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Header(navController)
            ToggleButton(isMonthSelected, onToggle = {
                isMonthSelected = it
                showContent = it
            })
            if (showContent) {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(horizontal = 16.dp)
                ) {
                    item { WordSections() }
                    item { Statistics() }
                    item { Spacer(modifier = Modifier.height(16.dp)) }
                    item { MoodChart() }
                    item { Spacer(modifier = Modifier.height(32.dp)) }
                    item { WordCloud() }
                }
            } else {
                Column (modifier = Modifier.padding(horizontal = 20.dp)){
                    Spacer(modifier = Modifier.height(40.dp))

                    CalendarView(
                        year = 2024,
                        month = 5,
                        daysWithData = daysWithData,
                        onDateSelected = { selectedDate = it }
                    )
                    Spacer(modifier = Modifier.height(30.dp))
                    selectedDate?.let { DataPreview(navController = navController, date = it) }
                }
            }
        }
    }
}

@Composable
fun Header(navController: NavController) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp, 16.dp, 16.dp, 10.dp)
            .height(56.dp),
        contentAlignment = Alignment.Center
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            IconButton(onClick = { navController.navigate("MonthSelection") }) {
                Icon(
                    Icons.Default.DateRange,
                    contentDescription = "Calendar Icon",
                    Modifier.size(35.dp)
                )
            }
            Text(
                text = "2024 May",
                fontSize = 26.sp,
                fontWeight = FontWeight.W400,
                color = Color.Black,
            )
            IconButton(onClick = { navController.navigate("search") }) {
                Icon(
                    Icons.Default.Search,
                    contentDescription = "Search Icon",
                    Modifier.size(35.dp)
                )
            }
        }
    }
}

@Composable
fun ToggleButton(isMonthSelected: Boolean, onToggle: (Boolean) -> Unit) {
    Box(
        modifier = Modifier.padding(30.dp,0.dp,30.dp,10.dp)
    ) {
        Row(
            modifier = Modifier
                .wrapContentSize()
                .background(Color(0xFFF0F0F0), RoundedCornerShape(10.dp))
                .padding(2.dp)
        ) {
            Box(modifier = Modifier
                .weight(1f)
                .background(
                    if (isMonthSelected) ThemeColor else Color.Transparent,
                    MaterialTheme.shapes.small
                )
                .clickable { onToggle(true) }
                .padding(vertical = 8.dp, horizontal = 16.dp),
                contentAlignment = Alignment.Center) {
                Text(
                    text = "月",
                    fontSize = 16.sp,
                    color = if (isMonthSelected) Color.Black else Color
                        .Gray
                )
            }

            Box(modifier = Modifier
                .weight(1f)
                .background(
                    if (!isMonthSelected) ThemeColor else Color.Transparent,
                    MaterialTheme.shapes.small
                )
                .clickable { onToggle(false) }
                .padding(vertical = 8.dp, horizontal = 16.dp),
                contentAlignment = Alignment.Center) {
                Text(
                    text = "日",
                    fontSize = 16.sp,
                    color = if (!isMonthSelected) Color.Black else Color.Gray
                )
            }
        }
    }
}

@Composable
fun WordSections() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Card(
            modifier = Modifier
                .weight(1f)
                .padding(8.dp)
                .fillMaxHeight(),
            colors = CardDefaults.cardColors(LightColor),
            shape = MaterialTheme.shapes.medium,
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(text = "貓咪", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                Text(text = "當提及這個詞彙，通常能讓你感到快樂。")
            }
        }
        Card(
            modifier = Modifier
                .weight(1f)
                .padding(8.dp)
                .fillMaxHeight(),
            colors = CardDefaults.cardColors(LightColor),
            shape = MaterialTheme.shapes.medium
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(text = "過敏", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                Text(text = "當提及這個詞彙，通常會讓你情緒較低落。")
            }
        }
    }
}

@Composable
fun Statistics() {
    Column {
        Card(
            modifier = Modifier.padding(8.dp),
            colors = CardDefaults.cardColors(LightColor),
            shape = MaterialTheme.shapes.medium
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(text = "這個月我們進行了 12 次對話，比起上個月多了 2 次。")
            }
        }
        Card(
            modifier = Modifier.padding(8.dp),
            colors = CardDefaults.cardColors(LightColor),
            shape = MaterialTheme.shapes.medium
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(text = "這個月你蒐集了 10 張卡牌，比起上個月多了 3 張。")
            }
        }
    }
}

@Composable
fun MoodChart() {
    Box(
        modifier = Modifier
            .padding(horizontal = 30.dp, vertical = 10.dp)
            .border(1.dp, Color.Black)
    ) {
        // 設置數據點
        val dataPoints = listOf(
            PointF(0f, 2f),
            PointF(0.5f, 4f),
            PointF(1f, 3f),
            PointF(1.5f, 8f),
            PointF(2f, 5f),
            PointF(2.5f, 7f),
            PointF(3f, 7f),
            PointF(3.57f, 4f),
            PointF(4f, 4f)
        )

        // 繪製折線圖
        Canvas(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .background(Color.LightGray)
        ) {
            val path = Path().apply {
                moveTo(
                    dataPoints[0].x * size.width / 4,
                    size.height - dataPoints[0].y * size.height / 10
                )
                dataPoints.forEach { point ->
                    lineTo(point.x * size.width / 4, size.height - point.y * size.height / 10)
                }
            }

            // 繪製折線
            drawPath(
                path = path,
                color = Color.Blue,
                style = Stroke(width = 4f)
            )

            // 繪製X軸和Y軸
            drawLine(
                color = Color.Black,
                start = Offset(0f, size.height),
                end = Offset(size.width, size.height),
                strokeWidth = 4f
            )
            drawLine(
                color = Color.Black,
                start = Offset(0f, 0f),
                end = Offset(0f, size.height),
                strokeWidth = 4f
            )

            // 繪製X軸標籤
            val xAxisPaint = Paint().apply {
                color = android.graphics.Color.BLACK
                textSize = 40f
            }
            for (i in 0..4) {
                drawContext.canvas.nativeCanvas.drawText(
                    "$i", i * size.width / 4, size.height + 40, xAxisPaint
                )
            }

            // 繪製Y軸標籤
            val yAxisPaint = Paint().apply {
                color = android.graphics.Color.BLACK
                textSize = 40f
            }
            for (i in 0..5) {
                drawContext.canvas.nativeCanvas.drawText(
                    "${i * 2}", -40f, size.height - i * size.height / 5, yAxisPaint
                )
            }
        }
    }
}

@Composable
fun WordCloud() {
    Box(
        modifier = Modifier
            .padding(horizontal = 30.dp, vertical = 10.dp)
//            .border(1.dp, Color.Black)
    ) {
        // 示例词汇及其权重
        val words = listOf(
            "Kotlin" to 10,
            "Compose" to 8,
            "Android" to 4,
            "Jetpack" to 2,
            "UI" to 5,
            "Development" to 4,
            "OpenAI" to 5,
            "AI" to 3
        )

        // 随机位置生成
        fun randomPosition(maxWidth: Float, maxHeight: Float): Pair<Float, Float> {
            val x = Random.nextFloat() * maxWidth
            val y = Random.nextFloat() * maxHeight
            return Pair(x, y)
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .background(Color.LightGray),
            contentAlignment = Alignment.Center
        ) {
            Canvas(modifier = Modifier.fillMaxSize()) {
                // 获取 Canvas 的尺寸
                val canvasWidth = size.width - 70.dp.toPx()
                val canvasHeight = size.height - 70.dp.toPx()

                // 绘制单词
                for ((word, weight) in words) {
                    val paint = Paint().apply {
                        color = android.graphics.Color.rgb(
                            (0..255).random(),
                            (0..255).random(),
                            (0..255).random()
                        )
                        textSize = weight * 10f
                    }

                    // 随机位置
                    val (x, y) = randomPosition(canvasWidth, canvasHeight)

                    drawContext.canvas.nativeCanvas.drawText(
                        word,
                        x,
                        y,
                        paint
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MonthScreenPreview() {
    val navController = rememberNavController()
    ProjectTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            MonthScreen(navController = rememberNavController())
        }
    }
}