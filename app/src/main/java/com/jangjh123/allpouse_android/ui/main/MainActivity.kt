package com.jangjh123.allpouse_android.ui.main

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.jangjh123.allpouse_android.R
import com.jangjh123.allpouse_android.ui.home.HomeScreen
import com.jangjh123.allpouse_android.ui.main.Screen.*
import com.jangjh123.allpouse_android.ui.main.ui.theme.AllPouseAndroidTheme
import com.jangjh123.allpouse_android.ui.theme.cinzelExtraBold

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AllPouseAndroidTheme {
                MainActivityContent(windowManager)
            }
        }
    }
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
private fun MainActivityContent(windowManager: WindowManager) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = navBackStackEntry?.destination

    Scaffold(
        topBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
                    .background(
                        color = Color.Black
                    )
            ) {
                Image(
                    modifier = Modifier
                        .padding(18.dp)
                        .size(24.dp)
                        .align(Alignment.TopStart),
                    painter = painterResource(id = R.drawable.ic_notification),
                    contentDescription = "notice",
                    contentScale = ContentScale.FillBounds,
                    colorFilter = ColorFilter.tint(color = Color.White)
                )

                Text(
                    modifier = Modifier.align(Alignment.Center),
                    text = stringResource(id = R.string.app),
                    color = Color.White,
                    style = TextStyle(
                        platformStyle = PlatformTextStyle(includeFontPadding = false)
                    ),
                    fontFamily = cinzelExtraBold,
                    fontSize = 24.sp,
                    letterSpacing = (-1).sp
                )

                Image(
                    modifier = Modifier
                        .padding(18.dp)
                        .size(24.dp)
                        .align(Alignment.TopEnd),
                    painter = painterResource(id = R.drawable.ic_search),
                    contentDescription = "search",
                    contentScale = ContentScale.FillBounds,
                    colorFilter = ColorFilter.tint(color = Color.White)
                )
            }
        },
        bottomBar = {
            BottomNavigation(
                modifier = Modifier
                    .height(60.dp)
                    .clip(shape = RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp)),
                backgroundColor = Color.Black
            ) {
                BottomNavigationItem(selected = currentScreen?.hierarchy?.any {
                    it.route == Home.route
                } == true, onClick = {
                    navController.navigate(Home.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                    selectedContentColor = Color.White,
                    unselectedContentColor = Color.Gray,
                    icon = {
                        Icon(
                            painter =
                            painterResource(id = R.drawable.ic_home),
                            contentDescription = "home"
                        )
                    }
                )



                BottomNavigationItem(selected = currentScreen?.hierarchy?.any {
                    it.route == Shop.route
                } == true, onClick = {
                    navController.navigate(Shop.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                    selectedContentColor = Color.White,
                    unselectedContentColor = Color.Gray,
                    icon = {
                        Icon(
                            painter =
                            painterResource(id = R.drawable.ic_shopping),
                            contentDescription = "shop"
                        )
                    }
                )

                BottomNavigationItem(selected = currentScreen?.hierarchy?.any {
                    it.route == Review.route
                } == true, onClick = {
                    navController.navigate(Review.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                    selectedContentColor = Color.White,
                    unselectedContentColor = Color.Gray,
                    icon = {
                        Icon(
                            painter =
                            painterResource(id = R.drawable.ic_review),
                            contentDescription = "review"
                        )
                    }
                )



                BottomNavigationItem(selected = currentScreen?.hierarchy?.any {
                    it.route == MyInfo.route
                } == true, onClick = {
                    navController.navigate(MyInfo.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                    selectedContentColor = Color.White,
                    unselectedContentColor = Color.Gray,
                    icon = {
                        Icon(
                            painter =
                            painterResource(id = R.drawable.ic_settings),
                            contentDescription = "myInfo"
                        )
                    }
                )
            }
        }
    ) {
        NavHost(navController = navController, startDestination = Home.route) {
            composable(Home.route) { HomeScreen(windowManager) }
            composable(Shop.route) { }
            composable(Review.route) { }
            composable(MyInfo.route) { }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun DefaultPreview() {
    AllPouseAndroidTheme {

    }
}

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Shop : Screen("shop")
    object Review : Screen("review")
    object MyInfo : Screen("myInfo")
}