package com.example.project

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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

data class DataItem(
    val dateTime: String,
    val mainText: String,
    val additionalText1: String,
    val additionalText2: String
)

val dataItems = listOf(
    DataItem(
        dateTime = "2024-08-25 11:02:53",
        mainText = "今天貓咪小花......",
        additionalText1 = "12",
        additionalText2 = "12:30"
    ),
    DataItem(
        dateTime = "2024-08-21 17:28:32",
        mainText = "示例文本1",
        additionalText1 = "15",
        additionalText2 = "15:45"
    )
)

@Composable
fun SearchScreen(navController: NavController) {
    var keyword by remember { mutableStateOf("") }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            IconButton(onClick = { navController.navigateUp() }) {
                Icon(
                    bitmap = ImageBitmap.imageResource(id = R.drawable.back),
                    contentDescription = null,
                    modifier = Modifier.size(26.dp)
                )
            }
            TextField(
                value = keyword,
                onValueChange = { keyword = it },
                placeholder = {
                    Text(
                        text = "輸入關鍵字...",
                        fontSize = 16.sp,
                        color = Color.Gray,
                    )
                },
                modifier = Modifier
                    .weight(1f)
                    .height(56.dp) // 设置输入框的高度为 56.dp，这是 Material Design 中的标准高度
            )
            IconButton(onClick = { /*TODO*/ }) {
                Icon(
                    Icons.Default.Search,
                    contentDescription = "Search Icon",
                    Modifier.size(35.dp)
                )
            }
        }
        SearchPreview(navController = navController, dataItems = dataItems, keyword = keyword)
    }
}
@Composable
fun SearchPreview(navController: NavController, dataItems: List<DataItem>, keyword: String) {
    val filteredItems = dataItems.filter { it.additionalText2.contains(keyword, ignoreCase = true) }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp)
    ) {
        items(filteredItems) { item ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
                    .clickable {
                        // Handle click if needed
                    },
                colors = CardDefaults.cardColors(containerColor = Color.White),
                shape = RoundedCornerShape(8.dp),
            ) {
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                ) {
                    Text(
                        text = item.mainText,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start
                    ) {
                        Icon(bitmap = ImageBitmap.imageResource(id = R.drawable.chat),
                            contentDescription = null,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(text = item.additionalText1, fontSize = 16.sp)
                        Text(text = "則對話", fontSize = 16.sp)
                        Spacer(modifier = Modifier.width(8.dp))
                        Icon(bitmap = ImageBitmap.imageResource(id = R.drawable.clock),
                            contentDescription = null,
                            modifier = Modifier.size(22.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        val timeParts = item.additionalText2.split(":")
                        val formattedTime = "${timeParts[0]}min ${timeParts[1]}sec"
                        Text(text = formattedTime, fontSize = 16.sp)
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SearchScreenPreview() {
    val navController = rememberNavController()
    ProjectTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            SearchScreen(navController = navController)
        }
    }
}
