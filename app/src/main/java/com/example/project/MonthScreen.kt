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
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
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
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.text.AnnotatedString
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
import com.patrykandpatrick.vico.core.axis.AxisItemPlacer
import com.patrykandpatrick.vico.core.entry.entryModelOf
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import kotlin.random.Random
import androidx.compose.ui.geometry.Rect as ComposeRect



@Composable
fun MonthScreen(
    navController: NavController,
//    formattedMonth: String,
    selectedMonth: String,
    allConversationData: List<String>
) {
    var showContent by remember { mutableStateOf(true) }
    var isMonthSelected by remember { mutableStateOf(true) }
    var selectedDate by remember { mutableStateOf<LocalDate?>(null) }

    val year = selectedMonth.split(".")[0].toInt()
    val month = selectedMonth.split(".")[1].toInt()

    // 取得被选取的 year 和 month 的所有数据的日期
    val daysWithData = allConversationData
        .filter { it.startsWith("$year.${month.toString().padStart(2, '0')}") }
        .map { it.split(".")[2].toInt() }
        .toSet()

    Surface(color = MaterialTheme.colorScheme.surface) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Header(navController, selectedMonth = selectedMonth)
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
                    item { Spacer(modifier = Modifier.height(16.dp)) }
                }
            } else {
                Column(modifier = Modifier.padding(horizontal = 20.dp)) {
                    Spacer(modifier = Modifier.height(40.dp))

                    CalendarView(
                        year = year,
                        month = month,
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
fun Header(navController: NavController, selectedMonth: String) {
    // Parse the year and month from the selectedMonth string
    val (year, month) = selectedMonth.split(".").map { it.toInt() }

    // Format the month name for display
    val monthName = LocalDate.of(year, month, 1).format(DateTimeFormatter.ofPattern("MMMM yyyy"))

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
            IconButton(onClick = { navController.navigate("home") }) {
                Icon(
                    bitmap = ImageBitmap.imageResource(id = R.drawable.back),
                    contentDescription = null,
                    modifier = Modifier.size(26.dp)
                )

            }
            ClickableText(
                text = AnnotatedString(monthName),
                style = androidx.compose.ui.text.TextStyle(
                    fontSize = 26.sp,
                    fontWeight = FontWeight.W400,
                    color = Black,
                ),
                onClick = { offset -> navController.navigate("MonthSelection")
                    // 执行点击事件的逻辑
                }
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
                    color = if (isMonthSelected) Black else Color
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
                    color = if (!isMonthSelected) Black else Color.Gray
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
//                錄九月的時候改追星
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
                Text(text = "作業", fontSize = 20.sp, fontWeight = FontWeight.Bold)
//                錄九月的時候改下雨
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
                Text(text = "這個月我們進行了 8 次對話，比起上個月多了 2 次。")
                //錄9月的時候改9,0
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
                Text(text = "這個月你蒐集了 6 張卡牌，比起上個月多了 3 張。")
                //錄9月的時候改2,少3
            }
        }
    }
}

@Composable
fun MoodChart() {
    val xLabels = listOf(
        "2", "5", "7", "10", "15", "19", "25", "29"
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp, vertical = 10.dp)
    ) {
        // 假設你有 12 筆資料，並用 entryModelOf 建立圖表資料
        val chartEntryModel = entryModelOf(4f, 9f, 5f, 7f, 6f, 3f, 7f, 6f)
                                                            //錄9月的時候改3f, 2f, 5f, 1f, 6f, 4f, 6f, 6f

        Chart(
            chart = lineChart(),
            model = chartEntryModel,
            startAxis = rememberStartAxis(), // Y 軸
            bottomAxis = rememberBottomAxis(
                itemPlacer = AxisItemPlacer.Horizontal.default(spacing = 1), // 每筆資料顯示一個標籤
                valueFormatter = { index, _ ->  // 加入第二個 ChartValues 參數，但不使用它
                    xLabels.getOrNull(index.toInt()) ?: ""
                }
            ),
        )
    }
}


@Composable
fun WordCloud() {
    Box(
        modifier = Modifier
                .fillMaxWidth()
            .padding(horizontal = 10.dp, vertical = 10.dp)
    ) {
        val words = listOf(
            "下課" to 2,
            "下雨" to 8,
            "第一名" to 4,
            "兔子" to 2,
            "星期六" to 5,
            "回家" to 4,
            "放假" to 5,
            "貓咪" to 3,
            "追星" to 20,
            "演唱會" to 8,
            "偶像" to 4,
            "唱歌" to 2,
            "中獎" to 5,
            "發財" to 4,
            "糖果" to 5,
            "飲料" to 3,
            "餅乾" to 20,
            "甜點" to 8,
            "生日" to 4,
            "喜歡" to 2,
            "好吃" to 5,
            "獲勝" to 4,
            "畢業" to 5,
            "開心" to 3
        )

        // 计算最大字体大小
        val maxFontSize = 100f
        val minFontSize = 10f
        val fontSizeFactor = maxFontSize / words.maxOfOrNull { it.second }!!

        fun isOverlap(rect1: ComposeRect, rect2: ComposeRect): Boolean {
            return !(
                    rect1.left + rect1.width < rect2.left ||
                            rect2.left + rect2.width < rect1.left ||
                            rect1.top + rect1.height < rect2.top ||
                            rect2.top + rect2.height < rect1.top
                    )
        }

        fun placeWord(canvasWidth: Float, canvasHeight: Float, wordBounds: ComposeRect,
                      weight: Int, placedWords: List<ComposeRect>): Pair<Float, Float>? {
            repeat(100) {
                val centerX = canvasWidth / 2
                val centerY = canvasHeight / 2

                val radius = (canvasWidth.coerceAtMost(canvasHeight) / 2) * (1 - weight / 20f)
                val angle = Random.nextFloat() * 2 * Math.PI

                val x = centerX + radius * Math.cos(angle).toFloat() - wordBounds.width / 2
                val y = centerY + radius * Math.sin(angle).toFloat() - wordBounds.height / 2

                val newWordBounds = ComposeRect(
                    left = x,
                    top = y,
                    right = x + wordBounds.width,
                    bottom = y + wordBounds.height
                )

                if (placedWords.none { isOverlap(it, newWordBounds) }) {
                    return Pair(x, y)
                }
            }
            return null
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .border(width = 1.dp, brush = SolidColor(Color.LightGray), shape = RoundedCornerShape(0.dp))
                .height(300.dp),
            contentAlignment = Alignment.Center
        )
        {
            Canvas(modifier = Modifier.fillMaxSize()) {
                val canvasWidth = size.width
                val canvasHeight = size.height
                val placedWords = mutableListOf<ComposeRect>()

                for ((word, weight) in words.sortedByDescending { it.second }) {
                    val fontSize = minFontSize + weight * fontSizeFactor
                    val paint = Paint().apply {
                        color = android.graphics.Color.rgb(
                            (0..255).random(),
                            (0..255).random(),
                            (0..255).random()
                        )
                        textSize = fontSize
                    }

                    val textBounds = android.graphics.Rect()
                    paint.getTextBounds(word, 0, word.length, textBounds)

                    val wordBounds = ComposeRect(
                        left = 0f,
                        top = 0f,
                        right = textBounds.width().toFloat(),
                        bottom = textBounds.height().toFloat()
                    )

                    val position = placeWord(canvasWidth, canvasHeight, wordBounds, weight, placedWords)

                    position?.let { (x, y) ->
                        placedWords.add(
                            ComposeRect(
                                left = x,
                                top = y,
                                right = x + wordBounds.width,
                                bottom = y + wordBounds.height
                            )
                        )
                        drawContext.canvas.nativeCanvas.drawText(
                            word, x, y + textBounds.height(), paint
                        )
                    }
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun MonthScreenPreview() {
    val navController = rememberNavController()
    val formattedMonth = "2024年08月" // 示例数据
    val selectedMonth = "2024.08"
    val allConversationData = listOf(
        "2024.08.01", "2024.08.15", "2024.08.30"
    ) // 示例数据

    ProjectTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            MonthScreen(
                navController = navController,
//                formattedMonth = formattedMonth,
                selectedMonth = selectedMonth,
                allConversationData = allConversationData
            )
        }
    }
}

