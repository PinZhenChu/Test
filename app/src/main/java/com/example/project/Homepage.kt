package com.example.project

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.project.ui.theme.ProjectTheme
import kotlinx.coroutines.launch

// menu
@Composable
fun DrawerContent() {

    Column(
        modifier = Modifier
            .height(250.dp)
            .width(200.dp)
            .padding(top = 16.dp)
    ) {
        val drawerState = rememberDrawerState(DrawerValue.Closed)
        val scope = rememberCoroutineScope()
        // icons to mimic drawer destinations
        val items = listOf(Icons.Default.Notifications, Icons.Default.Face, Icons.Default.Email)

        val names = listOf("通知", "頭貼", "")
        val selectedItem = remember { mutableStateOf(items[0]) }
        Spacer(Modifier.height(50.dp))
        items.zip(names).forEach { (item, name) ->
            NavigationDrawerItem(
                icon = { Icon(item, contentDescription = null) },
                label = { Text(name) },
                selected = item == selectedItem.value,
                onClick = {
                    scope.launch { drawerState.close() }
                    selectedItem.value = item
                },
                colors = NavigationDrawerItemDefaults.colors(
                    selectedContainerColor = ThemeColor, // Set selected background color
                    selectedTextColor = Color.White,
                    selectedIconColor = Color.White,
                    unselectedContainerColor = Color.Transparent, // Set unselected background color
                    unselectedTextColor = Color.Gray
                ),
                modifier = Modifier.padding(start = 10.dp)
            )
        }
    }
}


// home
//@OptIn(ExperimentalMaterialApi::class)
@Composable
fun Home(navController: NavController, selectedMonth: String) {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            DrawerContent()
        },
        content = {
            Column(
                Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .fillMaxSize()
//                    .padding(16.dp)
                    .background(color = Color(android.graphics.Color.parseColor("#f2f1f6"))),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                ConstraintLayout(
                    Modifier
                        .height(250.dp)
                        .fillMaxWidth()
                ) {
                    val menu = createRef()
                    IconButton(onClick = { scope.launch { drawerState.open() } },
                        modifier = Modifier
                            .constrainAs(menu) {
                                start.linkTo(parent.start, margin = 10.dp)
                                top.linkTo(parent.top, margin = 20.dp)
                            }) {
                        Icon(Icons.Filled.Menu, contentDescription = null,
                            modifier = Modifier
                                .size(36.dp)
                                .constrainAs(menu) {
                                })
                    }


                }
                Image(painter = painterResource(id = R.drawable.user), contentDescription = null)
                Button(
                    onClick = { navController.navigate("mode") },
                    colors = ButtonDefaults.buttonColors(containerColor = ThemeColor),
                    shape = CircleShape,
                    modifier = Modifier.padding(top = 30.dp)
                ) {
                    Text(
                        text = "開始聊天",
                        fontSize = 30.sp,
                    )
                }
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,

                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(60.dp)
                ) {
                    FilledIconButton(
                        onClick = { navController.navigate("month/$selectedMonth") } ,
                        colors = IconButtonDefaults.filledIconButtonColors(
                            containerColor = ThemeColor, // 设置按钮的背景颜色
                            contentColor = Color.White // 设置按钮内容的颜色（图标颜色）
                        ),
                        modifier = Modifier
                            .size(80.dp)
                    ) {
                        Column {
                            Icon(
                                bitmap = ImageBitmap.imageResource(id = R.drawable.diary),
                                contentDescription = null,
                                modifier = Modifier
                                    .size(36.dp)

                            )
                            Text(text = "日記")
                        }

                    }
                    FilledIconButton(
                        onClick = { navController.navigate("owningCards") },
                        colors = IconButtonDefaults.filledIconButtonColors(
                            containerColor = ThemeColor, // 设置按钮的背景颜色
                            contentColor = Color.White // 设置按钮内容的颜色（图标颜色）
                        ),
                        modifier = Modifier
                            .size(80.dp)
                    ) {
                        Column {
                            Icon(
                                bitmap = ImageBitmap.imageResource(id = R.drawable.imagegallery),
                                contentDescription = null,
                                modifier = Modifier
                                    .size(36.dp)
                            )
                            Text(text = "卡牌")
                        }


                    }
                }
            }
        }
    )
}


@Preview(showBackground = true)
@Composable
fun HomePreview() {
    val navController = rememberNavController()
    ProjectTheme {
        Home(navController, selectedMonth = "")
    }
}









