package com.example.project

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter

@Composable
fun CalendarView(
    year: Int,
    month: Int,
    daysWithData: Set<Int>,
    onDateSelected: (LocalDate) -> Unit
) {
    var selectedDate by remember { mutableStateOf<LocalDate?>(null) }

    val firstDayOfMonth = LocalDate.of(year, month, 1)
    val daysInMonth = YearMonth.of(year, month).lengthOfMonth()
    val firstDayOfWeek = firstDayOfMonth.dayOfWeek.value % 7 // 星期天为0，星期一为1

    Column(modifier = Modifier.padding(horizontal = 10.dp)) {
        // Display day headers (Sun, Mon, Tue, ...)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            listOf("Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat").forEach {
                Text(
                    text = it,
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Center,
                    fontSize = 16.sp,
                    color = if (it == "Sun" || it == "Sat") Color.Red else Color.Black
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Display calendar days
        val totalCells = (daysInMonth + firstDayOfWeek + 6) / 7 * 7
        val displayDays = (1..totalCells).map { if (it > firstDayOfWeek && it <= daysInMonth + firstDayOfWeek) it - firstDayOfWeek else 0 }

        displayDays.chunked(7).forEach { week ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                week.forEach { day ->
                    val isSelected = selectedDate?.dayOfMonth == day

                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .aspectRatio(1f)
                            .padding(4.dp)
                            .background(
                                color = if (daysWithData.contains(day)) LightColor else Color.Transparent,
                                shape = CircleShape
                            )
                            .clickable(enabled = daysWithData.contains(day) && day != 0) {
                                if (day != 0 && daysWithData.contains(day)) {
                                    val date = LocalDate.of(year, month, day)
                                    selectedDate = date
                                    onDateSelected(date)
                                }
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        if (day != 0) {
                            Text(
                                text = day.toString(),
                                color = Color.Black
                            )
                        }
                        if (isSelected) {
                            Canvas(modifier = Modifier.matchParentSize()) {
                                drawCircle(
                                    color = Color.Red,
                                    radius = size.minDimension / 2 - 2.dp.toPx(),
                                    style = Stroke(width = 2.dp.toPx())
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun DataPreview(navController: NavController, date: LocalDate) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = {
                // Use the formatted date for navigation
                val formattedDate = date.format(DateTimeFormatter.ISO_LOCAL_DATE)
                navController.navigate("Record/$formattedDate")
            }),
        colors = CardDefaults.cardColors(LightColor),
        shape = RoundedCornerShape(8.dp),
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "Date: ${date.format(DateTimeFormatter.ISO_LOCAL_DATE)}")
            Text(text = "Data for this date: Example data for ${date.dayOfMonth}.")
        }
    }
}



