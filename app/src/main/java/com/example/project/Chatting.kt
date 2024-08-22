package com.example.project

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialogDefaults.containerColor
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.BiasAlignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.project.ui.theme.ProjectTheme
import com.google.accompanist.insets.LocalWindowInsets
import com.google.accompanist.insets.navigationBarsWithImePadding

data class Message(val text: String, val isSentByUser: Boolean)

@Composable
fun Chatting(navController: NavController) {
    var message by remember { mutableStateOf("") }
    val messages = remember { mutableStateListOf(
        Message("這是預設在左邊的訊息1", false),
        Message("這是預設在左邊的訊息2", false),
        Message("這是預設在左邊的訊息3", false),
        Message("這是預設在左邊的訊息4", true),
        Message("李馬克，黃仁俊，李帝努，李楷燦，羅渽民，鍾辰樂，朴志晟", false),
        Message("NCT（Neo Culture Technology）是韓國SM娛樂於2016年推出的一個男子音樂組合，名稱寓意為“新文化技術”，代表著開放式的成員變動和全球化概念。NCT的獨特之處在於其多元化的分隊模式，根據不同的音樂風格和市場需求，分成多個小分隊進行活動。\n" +
                "\n" +
                "NCT 127是以首爾為活動據點的分隊，擁有強烈的音樂風格和出色的舞蹈實力。NCT Dream則主要由較年輕的成員組成，以青春活力的形象和音樂吸引年輕觀眾。此外，NCT U是一個靈活的單元，根據不同的歌曲需求進行成員組合。而WayV則是面向中國市場的分隊，用中文進行音樂創作和宣傳。\n" +
                "\n" +
                "NCT成員來自全球各地，包括韓國、中國、日本、泰國、加拿大、美國等地，這種國際化背景使他們能夠更好地與全球粉絲互動，並在各國展開活動。NCT的音樂風格多樣，涵蓋流行、嘻哈、電子等多種元素，展示了他們強大的音樂創作和表演能力。\n" +
                "\n" +
                "自出道以來，NCT在全球範圍內取得了不俗的成績，獲得了眾多音樂獎項和廣泛的粉絲支持。他們不斷挑戰和創新，通過音樂和舞蹈表現出色的實力和獨特的魅力，成為當今K-pop界的重要代表之一。", true)
    ) }

    // 计时器相关的状态
    var timeSpent by remember { mutableStateOf(0L) }
    var timerRunning by remember { mutableStateOf(true) }

    // 使用 LaunchedEffect 來啟動計時器
    LaunchedEffect(timerRunning) {
        while (timerRunning) {
            kotlinx.coroutines.delay(1000L)
            timeSpent += 1
        }
    }

    val minutes = timeSpent / 60
    val seconds = timeSpent % 60
    val timeDisplay = String.format("%02d:%02d", minutes, seconds)

    val listState = rememberLazyListState()
    LaunchedEffect(messages.size) {
        if (messages.isNotEmpty()) {
            listState.animateScrollToItem(messages.size - 1)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(android.graphics.Color.parseColor("#f2f1f6")))
            .navigationBarsWithImePadding()
            .systemBarsPadding()
    ) {

        Spacer(modifier = Modifier.height(10.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp)
        ) {
            IconButton(onClick = { navController.navigate("home") }) {
                Icon(
                    bitmap = ImageBitmap.imageResource(id = R.drawable.back),
                    contentDescription = null,
                    modifier = Modifier.size(26.dp)
                )
            }
//            Spacer(modifier = Modifier.weight(1f))

            // 显示计时器的时间
            Text(text = "$timeDisplay", fontSize = 18.sp, color = Color.Black)

//            Spacer(modifier = Modifier.width(10.dp))

            // 这里添加一个按钮来停止计时器
            Button(
                onClick = { timerRunning = false },
                colors = ButtonDefaults.buttonColors(containerColor = BtnColor),
                modifier = Modifier
                    .clip(CircleShape)
                    .width(80.dp) // 设置按钮宽度为 120dp，你可以根据需要调整这个值
            ) {
                Text(text = "結束對話", color = Color.White)
            }



        }

        LazyColumn(
            state = listState,
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            items(messages) { msg ->
                MessageItem(message = msg.text, isSentByUser = msg.isSentByUser)
            }
        }
        Spacer(modifier = Modifier.height(8.dp))

        Row(modifier = Modifier.padding(start = 10.dp, end = 10.dp, bottom = 10.dp)) {
            TextField(
                value = message,
                onValueChange = { message = it },
                placeholder = { Text(text = "輸入文字..", color = Color.Gray) },
                shape = RoundedCornerShape(50.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedContainerColor = Color.White,
                    focusedContainerColor = Color.White,
                    focusedBorderColor = Color.Transparent,
                    unfocusedBorderColor = Color.Transparent,
                    unfocusedLabelColor = Color(android.graphics.Color.parseColor("#5e5e5e")),
                    focusedTextColor = Color(android.graphics.Color.parseColor("#5e5e5e")),
                    unfocusedTextColor = Color(android.graphics.Color.parseColor("#5e5e5e"))
                ),
                trailingIcon = {
                    Button(
                        onClick = {
                            if (message.isNotBlank()) {
                                messages.add(Message(message, true))
                                message = ""
                            }
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Transparent,
                            contentColor = (ThemeColor)
                        )
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.send),
                            contentDescription = null,
                            modifier = Modifier.size(30.dp)
                        )
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(3.dp, shape = RoundedCornerShape(50.dp))
                    .background(Color.White, CircleShape)
            )
        }
    }
}

@Composable
fun MessageItem(message: String, isSentByUser: Boolean) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = if (isSentByUser) Arrangement.End else Arrangement.Start
    ) {
        Column(
            modifier = Modifier
                .background(
//                    e5dfd4
                    color = if (isSentByUser) ThemeColor else LightColor,
                    shape = RoundedCornerShape(30.dp)
                )
                .padding(8.dp)
        ) {

            Box(
                modifier = Modifier
                    .widthIn(max = with(LocalDensity.current) { (LocalConfiguration.current.screenWidthDp * 1.75f).toDp() })
                    .padding(8.dp)
            ) {
                Text(
                    text = message,
                    color =  Color(android.graphics.Color.parseColor("#433217")),
                    textAlign = TextAlign.Start
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ChattingPreview() {
    val navController = rememberNavController()
    ProjectTheme {
        Chatting(navController = navController)
    }
}