package com.jangjh123.allpouse_android.ui.main.search

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jangjh123.allpouse_android.R
import com.jangjh123.allpouse_android.ui.component.APTextField
import com.jangjh123.allpouse_android.ui.theme.background
import com.jangjh123.allpouse_android.ui.theme.mainTextColor

@Composable
fun SearchScreen() {
    Box(modifier = Modifier
        .fillMaxSize()
        .background(color = background())) {
        Row(Modifier
            .padding(top = 12.dp)
            .fillMaxWidth()) {
            val searchState = remember { mutableStateOf("") }
            val searchFocusManager = LocalFocusManager.current

            Icon(
                modifier = Modifier
                    .weight(0.15f)
                    .align(CenterVertically),
                painter = painterResource(id = R.drawable.ic_arrow_back),
                contentDescription = "goBack",
                tint = mainTextColor())

            APTextField(modifier = Modifier
                .weight(0.7f),
                textFieldState = searchState,
                onValueChanged = { searchState.value = it },
                focusManager = searchFocusManager)

            Icon(
                modifier = Modifier
                    .weight(0.15f)
                    .align(CenterVertically),
                painter = painterResource(id = R.drawable.ic_search),
                contentDescription = "search",
                tint = mainTextColor())
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    SearchScreen()
}