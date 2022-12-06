package com.jangjh123.allpouse_android.ui.main.search

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterEnd
import androidx.compose.ui.Alignment.Companion.CenterStart
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.jangjh123.allpouse_android.R
import com.jangjh123.allpouse_android.ui.component.APText
import com.jangjh123.allpouse_android.ui.component.APTextField
import com.jangjh123.allpouse_android.ui.component.addFocusCleaner
import com.jangjh123.allpouse_android.ui.component.clickableWithoutRipple
import com.jangjh123.allpouse_android.ui.main.search.SearchKeywordType.Popular
import com.jangjh123.allpouse_android.ui.main.search.SearchKeywordType.Recent
import com.jangjh123.allpouse_android.ui.theme.*

val dummyRecentSearchKeyword = listOf("돌체앤가바나", "베르사체", "샤넬")
val dummyPopularSearchKeyword = listOf("디올", "샤넬", "딥티크")

@Composable
fun SearchScreen(navController: NavController) {
    val focusManager = LocalFocusManager.current
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = background())
            .addFocusCleaner(focusManager)
    ) {
        val searchTextState = remember { mutableStateOf("") }
        val typeState = remember { mutableStateOf<SearchKeywordType>(Recent) }

        Row(
            Modifier
                .padding(top = 12.dp)
                .fillMaxWidth()
        ) {
            Icon(
                modifier = Modifier
                    .weight(0.15f)
                    .align(CenterVertically)
                    .clickableWithoutRipple {
                        navController.popBackStack()
                    },
                painter = painterResource(id = R.drawable.ic_arrow_back),
                contentDescription = "goBack",
                tint = mainTextColor()
            )

            APTextField(
                modifier = Modifier
                    .weight(0.7f),
                textFieldState = searchTextState,
                onValueChanged = { searchTextState.value = it },
                focusManager = focusManager
            )

            Icon(
                modifier = Modifier
                    .weight(0.15f)
                    .align(CenterVertically),
                painter = painterResource(id = R.drawable.ic_search),
                contentDescription = "search",
                tint = mainTextColor()
            )
        }

        Row(
            Modifier
                .padding(12.dp)
                .clip(shape = RoundedCornerShape(12.dp))
                .fillMaxWidth()
                .height(40.dp)
                .background(subBackground())
        ) {

            SearchKeywordTypeButton(
                modifier = Modifier
                    .weight(0.5f)
                    .clickableWithoutRipple {
                        typeState.value = Recent
                    },
                type = stringResource(id = R.string.recent_search_keyword),
                typeState = typeState
            )

            SearchKeywordTypeButton(
                modifier = Modifier
                    .weight(0.5f)
                    .clickableWithoutRipple {
                        typeState.value = Popular
                    },
                type = stringResource(id = R.string.popular_search_keyword),
                typeState = typeState
            )
        }

        LazyColumn(
            modifier = Modifier
                .padding(12.dp)
                .clip(shape = RoundedCornerShape(12.dp))
                .fillMaxWidth()
                .fillMaxHeight()
                .background(color = subBackground())
        ) {
            item {
                Spacer(modifier = Modifier.height(8.dp))
            }

            items(
                when (typeState.value) {
                    is Recent -> {
                        dummyRecentSearchKeyword
                    }
                    is Popular -> {
                        dummyPopularSearchKeyword
                    }
                }
            ) { searchKeyword ->
                Box(
                    Modifier
                        .padding(
                            horizontal = 12.dp,
                            vertical = 4.dp
                        )
                        .clip(shape = RoundedCornerShape(12.dp))
                        .background(
                            color = contentBackground()
                        )
                        .fillMaxWidth(),
                ) {
                    APText(
                        modifier = Modifier
                            .padding(12.dp)
                            .align(CenterStart),
                        text = searchKeyword,
                        fontSize = 14.sp
                    )

                    Icon(
                        modifier = Modifier
                            .padding(end = 12.dp)
                            .size(20.dp)
                            .align(CenterEnd),
                        painter = painterResource(id = R.drawable.close),
                        contentDescription = "removeSearchKeyword",
                        tint = subTextColor()
                    )
                }
            }
        }
    }
}

sealed class SearchKeywordType {
    object Recent : SearchKeywordType()
    object Popular : SearchKeywordType()
}

@Composable
private fun SearchKeywordTypeButton(
    modifier: Modifier,
    type: String,
    typeState: MutableState<SearchKeywordType>
) {
    val buttonColorState =
        animateColorAsState(
            targetValue = if (typeState.value == Recent && type == stringResource(id = R.string.recent_search_keyword)) mainColor() else if (
                typeState.value == Popular && type == stringResource(id = R.string.popular_search_keyword)) mainColor() else
                subBackground()
        )

    val textColorState =
        animateColorAsState(
            targetValue = if (typeState.value == Recent && type == stringResource(id = R.string.recent_search_keyword)) Color.White else if (
                typeState.value == Popular && type == stringResource(id = R.string.popular_search_keyword)) Color.White else
                mainTextColor()
        )

    Box(
        modifier = modifier
            .background(color = buttonColorState.value)
            .fillMaxHeight()
    ) {
        APText(
            modifier = Modifier.align(Center),
            text = type,
            fontColor = textColorState.value
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
//    SearchScreen()
}