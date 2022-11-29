package com.jangjh123.allpouse_android.ui.home

import android.os.Build
import android.view.WindowManager
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.jangjh123.allpouse_android.R

val dummyAds = listOf(R.drawable.ad_banner_1, R.drawable.ad_banner_2)

@RequiresApi(Build.VERSION_CODES.R)
@Composable
fun HomeScreen(windowManager: WindowManager) {
    val scrollState = rememberScrollState()
    Box(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
    ) {
        val displayMetrics = windowManager.currentWindowMetrics
        val widthPx = displayMetrics.bounds.width()
        val calculatedHeight = androidx.compose.ui.platform.LocalDensity.current.run {
            (widthPx / 16 * 9).toDp()
        }

        println(calculatedHeight)

        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .height(calculatedHeight)
        ) {
            items(dummyAds) { ad ->
                Image(
                    modifier = Modifier
                        .fillMaxSize(),
                    painter = painterResource(id = ad),
                    contentDescription = "adBannerImage",
                    contentScale = ContentScale.Fit
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
//    HomeScreen()
}