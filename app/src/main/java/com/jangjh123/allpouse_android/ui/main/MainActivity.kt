package com.jangjh123.allpouse_android.ui.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
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
import androidx.navigation.compose.currentBackStackEntryAsState
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.jangjh123.allpouse_android.R
import com.jangjh123.allpouse_android.ui.main.Screen.*
import com.jangjh123.allpouse_android.ui.main.home.HomeScreen
import com.jangjh123.allpouse_android.ui.theme.AllPouseAndroidTheme
import com.jangjh123.allpouse_android.ui.theme.cinzelExtraBold
import com.jangjh123.allpouse_android.ui.theme.mainColor

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AllPouseAndroidTheme {
                MainActivityContent()
            }
        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
private fun MainActivityContent() {
    val navController = rememberAnimatedNavController()
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
                BottomNavigationItem(
                    selected = currentScreen?.hierarchy?.any {
                        it.route == Home.route
                    } == true,
                    onClick = {
                        navController.navigate(Home.route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    selectedContentColor = mainColor(),
                    unselectedContentColor = Color.Gray,
                    icon = {
                        Icon(
                            painter =
                            painterResource(id = R.drawable.ic_home),
                            contentDescription = "home"
                        )
                    }
                )

                BottomNavigationItem(
                    selected = currentScreen?.hierarchy?.any {
                        it.route == Shop.route
                    } == true,
                    onClick = {
                        navController.navigate(Shop.route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    selectedContentColor = mainColor(),
                    unselectedContentColor = Color.Gray,
                    icon = {
                        Icon(
                            painter =
                            painterResource(id = R.drawable.ic_shopping),
                            contentDescription = "shop"
                        )
                    }
                )

                BottomNavigationItem(
                    selected = currentScreen?.hierarchy?.any {
                        it.route == Review.route
                    } == true,
                    onClick = {
                        navController.navigate(Review.route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    selectedContentColor = mainColor(),
                    unselectedContentColor = Color.Gray,
                    icon = {
                        Icon(
                            painter =
                            painterResource(id = R.drawable.ic_review),
                            contentDescription = "review"
                        )
                    }
                )

                BottomNavigationItem(
                    selected = currentScreen?.hierarchy?.any {
                        it.route == MyInfo.route
                    } == true,
                    onClick = {
                        navController.navigate(MyInfo.route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    selectedContentColor = mainColor(),
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
    ) { scaffoldPadding ->
        AnimatedNavHost(
            modifier = Modifier.padding(scaffoldPadding),
            navController = navController,
            startDestination = Home.route,
            enterTransition = {
                slideIntoContainer(AnimatedContentScope.SlideDirection.Left,
                    animationSpec = tween(300))
            },
            exitTransition = {
                slideOutOfContainer(AnimatedContentScope.SlideDirection.Left,
                    animationSpec = tween(300))
            },
            popEnterTransition = {
                slideIntoContainer(AnimatedContentScope.SlideDirection.Right,
                    animationSpec = tween(300))
            },
            popExitTransition = {
                slideOutOfContainer(AnimatedContentScope.SlideDirection.Right,
                    animationSpec = tween(300))
            }
        ) {
            composable(Home.route) { HomeScreen() }
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