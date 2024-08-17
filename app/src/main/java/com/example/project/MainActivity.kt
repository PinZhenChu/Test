package com.example.project

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.project.ui.theme.ProjectTheme
import io.realm.kotlin.mongodb.App
import io.realm.kotlin.mongodb.Credentials
import kotlinx.coroutines.runBlocking
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

val ThemeColor = Color(213, 191, 160)//0xFFD5BFA0
val LightColor = ThemeColor.copy(alpha = 0.2f) //原0xFFE9E8E5

val allConversationData = listOf(
    "2024.05.01.09:00:00", "2024.05.03.10:15:30", "2024.05.05.14:20:00",
    "2024.05.07.16:45:10", "2024.05.10.13:00:00", "2024.05.12.11:05:20",
    "2024.05.15.08:30:45", "2024.05.17.17:15:00", "2024.05.20.19:20:30",
    "2024.05.22.12:40:10", "2024.05.25.18:10:00", "2024.05.27.21:35:50",
    "2024.04.02.11:10:00", "2024.04.04.08:20:15", "2024.04.06.15:45:25",
    "2024.04.08.14:30:00", "2024.04.10.13:15:10", "2024.04.12.17:50:45",
    "2024.04.14.19:25:30", "2024.04.16.20:30:10", "2024.04.18.11:45:00",
    "2024.04.20.14:50:30", "2024.04.22.09:05:20", "2024.04.24.10:20:00",
    "2024.04.26.16:45:10", "2024.04.28.13:00:00", "2024.04.30.19:30:45",
    "2024.04.01.09:15:30", "2024.04.03.17:10:00", "2024.04.05.18:20:10",
    "2024.04.07.11:25:45", "2024.03.03.10:15:00", "2024.03.05.12:30:20",
    "2024.03.07.14:50:10", "2024.03.09.09:00:30", "2024.03.11.08:20:45",
    "2024.03.13.13:10:15", "2024.03.15.15:45:20", "2024.03.17.11:35:00",
    "2024.03.19.18:00:30", "2024.03.21.21:15:10", "2024.03.23.10:30:45",
    "2024.03.25.12:45:00", "2024.03.27.14:10:20", "2024.03.29.19:20:15",
    "2024.03.31.17:05:10", "2024.02.02.08:30:00", "2024.02.04.11:15:20",
    "2024.02.06.13:45:10", "2024.02.08.09:50:30", "2024.02.10.12:00:45",
    "2024.02.12.14:25:15", "2024.02.14.16:40:20", "2024.02.16.10:35:00",
    "2024.02.18.13:50:30", "2024.02.20.15:10:45", "2024.01.08.10:15:10",
    "2024.01.20.15:05:00", "2023.12.01.09:00:00", "2023.12.05.10:15:30",
    "2023.12.10.11:20:10", "2023.12.15.14:45:00", "2023.12.20.18:30:15",
    "2023.11.02.09:10:00", "2023.11.04.11:20:30", "2023.11.06.13:45:10",
    "2023.11.08.15:00:20", "2023.11.10.09:15:30", "2023.11.12.10:25:45",
    "2023.11.14.12:30:00", "2023.11.16.14:45:10", "2023.11.18.16:20:30",
    "2023.11.20.09:05:15", "2023.11.22.11:10:00", "2023.11.24.13:35:00",
    "2023.11.26.14:20:10", "2023.11.28.16:55:30", "2023.11.30.18:10:00",
    "2023.10.01.10:00:00", "2023.10.05.12:15:30", "2023.10.09.14:45:00",
    "2023.10.13.16:20:10", "2023.10.17.11:30:00", "2023.10.21.13:00:30",
    "2023.10.25.15:10:45", "2023.10.29.17:25:00", "2023.10.30.19:15:20",
    "2023.10.31.20:00:30", "2023.09.03.08:45:00", "2023.09.07.10:30:15",
    "2023.09.11.14:20:00", "2023.09.15.16:05:10", "2023.09.19.09:20:30",
    "2023.09.23.11:50:00", "2023.09.27.14:30:20", "2023.09.29.17:10:10",
    "2023.09.30.18:45:00", "2023.08.02.08:20:00", "2023.08.06.10:10:15",
    "2023.08.10.12:45:30", "2023.08.14.14:30:00", "2023.08.18.16:05:20",
    "2023.08.22.11:15:00", "2023.08.26.12:30:10", "2023.08.30.14:45:00",
    "2023.08.31.16:00:15", "2023.07.01.09:15:00", "2023.07.05.11:20:10",
    "2023.07.09.13:30:00", "2023.07.13.15:20:30", "2023.07.17.16:45:00",
    "2023.07.21.08:30:15", "2023.07.25.11:00:00", "2023.07.29.13:20:45",
    "2023.06.05.10:15:00"
)

class MainActivity : ComponentActivity() {
    private lateinit var app: App
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        app = App.create("application-0-fbwwmjb")
        runBlocking {
            val credentials = Credentials.anonymous(true)
            val user = app.login(credentials)

            Log.i("TAG", "onCreate: user logged in successfully uid = ${user.id}")
        }
        setContent {
            ProjectTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MyApp()
                }
            }
        }
    }
}

@Composable
fun MyApp() {
    val navController = rememberNavController()
    val currentDate = LocalDate.now()
    val currentMonth = "${currentDate.year}.${currentDate.monthValue.toString().padStart(2, '0')}"

    NavHost(navController = navController, startDestination = "home") {
        composable("home") { Home(navController, currentMonth) }
        composable("mode") { ChoosingMode(navController) }
        composable("cards") { ChoosingCards() }
        composable("owningCards") { OwningCards() }
        composable("chatting") { Chatting() }
        composable("month/{selectedMonth}") { backStackEntry ->
            val selectedMonth = backStackEntry.arguments?.getString("selectedMonth")
            selectedMonth?.let {
                MonthScreen(
                    navController = navController,
                    selectedMonth = it,
                    allConversationData = allConversationData
                )
            }
        }
        composable("search") { SearchScreen(navController) }
        composable("MonthSelection") { MonthSelectionScreen(navController, allConversationData = allConversationData) }
        composable("Record/{date}") { backStackEntry ->
            val dateString = backStackEntry.arguments?.getString("date")
            val date = dateString?.let { LocalDate.parse(it, DateTimeFormatter.ISO_LOCAL_DATE) }
            RecordScreen(navController, date = date)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ProjectTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            MyApp()
        }
    }
}
