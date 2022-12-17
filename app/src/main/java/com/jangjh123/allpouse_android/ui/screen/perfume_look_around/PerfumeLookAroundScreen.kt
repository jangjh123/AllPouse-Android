package com.jangjh123.allpouse_android.ui.screen.perfume_look_around

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jangjh123.allpouse_android.R
import com.jangjh123.allpouse_android.ui.component.APText
import com.jangjh123.allpouse_android.ui.component.Keyword
import com.jangjh123.allpouse_android.ui.component.clickableWithoutRipple
import com.jangjh123.allpouse_android.ui.screen.main.home.dummyTasteKeyword
import com.jangjh123.allpouse_android.ui.screen.perfume_look_around.PerfumeLookAroundMode.UserChoice
import com.jangjh123.allpouse_android.ui.screen.perfume_look_around.PerfumeLookAroundMode.UserDefault
import com.jangjh123.allpouse_android.ui.screen.splash.SCREEN_WIDTH_DP
import com.jangjh123.allpouse_android.ui.theme.*

sealed class PerfumeLookAroundMode {
    object UserDefault : PerfumeLookAroundMode()
    object UserChoice : PerfumeLookAroundMode()
}

@Composable
fun PerfumeLookAroundScreen() {
    val scrollState = rememberScrollState()
    val modeState =
        remember { mutableStateOf<PerfumeLookAroundMode>(UserDefault) }
    val keywordBlockHeightState = animateDpAsState(
        targetValue = if (modeState.value == UserDefault) 60.dp else 310.dp)
        val testChosenKeywords = remember { mutableStateListOf<String>() }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                color = background()
            )
            .verticalScroll(
                state = scrollState
            )
    ) {
        // 내 키워드로 검색이 default
        // 키워드 직접 선택 (최대 5개 까지)


        Row(
            modifier = Modifier
                .fillMaxSize()
                .height(50.dp)
        ) {
            PerfumeLookAroundModeButton(
                modifier = Modifier
                    .height(50.dp)
                    .weight(0.5f),
                mode = stringResource(
                    id = R.string.depend_on_user_default
                ),
                modeState = modeState
            ) {
                modeState.value = UserDefault
            }
            PerfumeLookAroundModeButton(
                modifier = Modifier
                    .height(50.dp)
                    .weight(0.5f),
                mode = stringResource(
                    id = R.string.depend_on_user_choice
                ),
                modeState = modeState
            ) {
                modeState.value = UserChoice
            }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(keywordBlockHeightState.value)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        color = subBackground()
                    )
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp)
                ) {
                    APText(
                        modifier = Modifier
                            .weight(0.4f)
                            .align(CenterVertically),
                        text = when (modeState.value) {
                            UserDefault -> {
                                stringResource(
                                    id = R.string.taste_keyword
                                )
                            }
                            UserChoice -> {
                                stringResource(
                                    id = R.string.chosen_keywords
                                )
                            }
                        }
                    )

                    Divider(
                        modifier = Modifier
                            .padding(
                                vertical = 16.dp
                            )
                            .fillMaxHeight()
                            .width(1.dp),
                        color = mainColor()
                    )

                    LazyRow(
                        modifier = Modifier
                            .padding(
                                horizontal = 12.dp
                            )
                            .weight(0.6f)
                            .align(CenterVertically)
                    ) {
                        items(
                            when (modeState.value) {
                                UserDefault -> {
                                    dummyTasteKeyword
                                }
                                UserChoice -> {
                                    testChosenKeywords
                                }
                            }
                        ) { keyword ->
                            Keyword(
                                modifier = Modifier
                                    .padding(
                                        horizontal = 4.dp
                                    )
                                    .align(CenterVertically),
                                keyword = keyword
                            )
                        }
                    }
                }

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                ) {
                    APText(
                        modifier = Modifier
                            .align(Center),
                        text = stringResource(
                            id = R.string.choose_keywords
                        ),
                        fontSize = 18.sp
                    )
                }

                val keywords = listOf(
                    "테스트0",
                    "테스트1",
                    "테스트2",
                    "테스트3",
                    "테스트4",
                    "테스트5",
                    "테스트6",
                    "테스트7",
                    "테스트8",
                    "테스트9",
                    "테스트10",
                    "테스트11",
                    "테스트12",
                    "테스트13",
                    "테스트14",
                    "테스트15",
                )

                if (modeState.value == UserChoice) {
                    LazyHorizontalGrid(
                        modifier = Modifier
                            .padding(
                                vertical = 4.dp
                            )
                            .fillMaxWidth()
                            .height(200.dp),
                        rows = GridCells.Fixed(4)
                    ) {
                        items(keywords) { keyword ->
                            val keywordChoiceState = remember { mutableStateOf(false) }
                            val textColorState =
                                animateColorAsState(targetValue = if (keywordChoiceState.value) mainColor() else mainTextColor())

                            Box(
                                modifier = Modifier
                                    .width(SCREEN_WIDTH_DP / 4)
                                    .height(50.dp)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .padding(4.dp)
                                        .clip(
                                            shape = RoundedCornerShape(12.dp)
                                        )
                                        .fillMaxSize()
                                        .background(
                                            color = contentBackground()
                                        )
                                        .clickableWithoutRipple {
                                            keywordChoiceState.value = true
                                            testChosenKeywords.add(keyword)
                                        }
                                ) {
                                    APText(
                                        modifier = Modifier
                                            .align(Center),
                                        text = keyword,
                                        fontColor = textColorState.value
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun PerfumeLookAroundModeButton(
    modifier: Modifier,
    mode: String,
    modeState: MutableState<PerfumeLookAroundMode>,
    onClick: () -> Unit
) {
    val textColorState = animateColorAsState(
        targetValue = if (modeState.value == UserDefault && mode == stringResource(
                id = R.string.depend_on_user_default
            )
        ) Color.White
        else if (modeState.value == UserChoice && mode == stringResource(
                id = R.string.depend_on_user_choice
            )
        ) Color.White
        else mainTextColor()
    )

    val buttonColorState = animateColorAsState(
        targetValue = if (modeState.value == UserDefault && mode == stringResource(
                id = R.string.depend_on_user_default
            )
        ) mainColor()
        else if (modeState.value == UserChoice && mode == stringResource(
                id = R.string.depend_on_user_choice
            )
        ) mainColor()
        else contentBackground()
    )

    Box(
        modifier = modifier
            .background(
                color = buttonColorState.value
            )
            .clickableWithoutRipple {
                onClick()
            }
    ) {
        APText(
            modifier = Modifier
                .align(Center),
            text = mode,
            fontColor = textColorState.value
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    PerfumeLookAroundScreen()
}