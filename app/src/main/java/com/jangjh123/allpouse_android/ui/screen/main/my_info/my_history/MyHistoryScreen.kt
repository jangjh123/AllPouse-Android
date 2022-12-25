package com.jangjh123.allpouse_android.ui.screen.main.my_info.my_history

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jangjh123.allpouse_android.R
import com.jangjh123.allpouse_android.ui.component.APAppendedText
import com.jangjh123.allpouse_android.ui.component.APText
import com.jangjh123.allpouse_android.ui.component.DummyComment.DummyReviewComment
import com.jangjh123.allpouse_android.ui.component.FontType
import com.jangjh123.allpouse_android.ui.component.clickableWithoutRipple
import com.jangjh123.allpouse_android.ui.screen.detail.review_detail.dummyReviewComments
import com.jangjh123.allpouse_android.ui.screen.main.home.dummyAds
import com.jangjh123.allpouse_android.ui.screen.main.my_info.my_history.DummyHistoryPeriod.*
import com.jangjh123.allpouse_android.ui.screen.main.my_info.my_history.DummyHistoryType.*
import com.jangjh123.allpouse_android.ui.theme.*

sealed class DummyHistoryType {
    object MyLike : DummyHistoryType()
    object MyReview : DummyHistoryType()
    object MyComment : DummyHistoryType()
}

@Composable
fun MyHistoryScreen(
    type: DummyHistoryType
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
    ) {

        item {
            APText(
                modifier = Modifier
                    .padding(12.dp),
                text = stringResource(
                    id = when (type) {
                        MyLike -> {
                            R.string.my_like
                        }
                        MyReview -> {
                            R.string.my_review
                        }
                        MyComment -> {
                            R.string.my_comment
                        }
                    }
                ),
                fontSize = 20.sp,
                fontType = FontType.Bold
            )
        }

        item {
            val historyPeriodState =
                remember { mutableStateOf<DummyHistoryPeriod>(Week) }

            Column(
                modifier = Modifier
                    .padding(12.dp)
                    .clip(
                        shape = RoundedCornerShape(12.dp)
                    )
                    .fillMaxWidth()
                    .background(
                        color = subBackground()
                    )
            ) {
                APText(
                    modifier = Modifier
                        .padding(24.dp)
                        .align(CenterHorizontally),
                    text = stringResource(
                        id = R.string.set_period
                    ),
                    fontSize = 18.sp
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp)
                ) {
                    HistoryPeriodButton(
                        modifier = Modifier
                            .padding(
                                start = 12.dp,
                                end = 6.dp
                            )
                            .weight(0.25f)
                            .height(50.dp),
                        historyPeriod = stringResource(
                            id = R.string.week
                        ),
                        periodState = historyPeriodState
                    ) {
                        historyPeriodState.value = Week
                    }

                    HistoryPeriodButton(
                        modifier = Modifier
                            .padding(
                                horizontal = 6.dp
                            )
                            .weight(0.25f)
                            .height(50.dp),
                        historyPeriod = stringResource(
                            id = R.string.month
                        ),
                        periodState = historyPeriodState
                    ) {
                        historyPeriodState.value = Month
                    }

                    HistoryPeriodButton(
                        modifier = Modifier
                            .padding(
                                horizontal = 6.dp
                            )
                            .weight(0.25f)
                            .height(50.dp),
                        historyPeriod = stringResource(
                            id = R.string.halfOfYear
                        ),
                        periodState = historyPeriodState
                    ) {
                        historyPeriodState.value = HalfOfYear
                    }

                    HistoryPeriodButton(
                        modifier = Modifier
                            .padding(
                                start = 6.dp,
                                end = 12.dp
                            )
                            .weight(0.25f)
                            .height(50.dp),
                        historyPeriod = stringResource(
                            id = R.string.year
                        ),
                        periodState = historyPeriodState
                    ) {
                        historyPeriodState.value = Year
                    }
                }
            }
        }

        item {
            DateItem(
                date = "2022년 12월 19일"
            )
        }

        items(
            when (type) {
                MyLike -> {
                    dummyAds
                }
                MyReview -> {
                    dummyAds
                }
                MyComment -> {
                    dummyReviewComments
                }
            }
        ) { item ->
            when (type) {
                MyLike -> {

                }
                MyReview -> {

                }
                MyComment -> {
                    val comment = item as DummyReviewComment
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                color = subBackground()
                            )
                    ) {
                        MyComment(
                            modifier = Modifier,
                            title = "테스트 향수 리뷰",
                            date = comment.date,
                            body = "테스트 댓글입니다."
                        )

                        Divider(
                            modifier = Modifier
                                .fillMaxWidth(),
                            thickness = 1.dp,
                            color = contentBackground()
                        )
                    }
                }
            }
        }
    }
}

sealed class DummyHistoryPeriod {
    object Week : DummyHistoryPeriod()
    object Month : DummyHistoryPeriod()
    object HalfOfYear : DummyHistoryPeriod()
    object Year : DummyHistoryPeriod()
}


@Composable
private fun HistoryPeriodButton(
    modifier: Modifier,
    historyPeriod: String,
    periodState: MutableState<DummyHistoryPeriod>,
    onClick: () -> Unit
) {
    val fontColorState = animateColorAsState(
        targetValue =
        if (periodState.value == Week && historyPeriod == stringResource(
                id = R.string.week
            )
        ) Color.White
        else if (periodState.value == Month && historyPeriod == stringResource(
                id = R.string.month
            )
        ) Color.White
        else if (periodState.value == HalfOfYear && historyPeriod == stringResource(
                id = R.string.halfOfYear
            )
        ) Color.White
        else if (periodState.value == Year && historyPeriod == stringResource(
                id =
                R.string.year
            )
        ) Color.White
        else {
            mainTextColor()
        }
    )

    val buttonColorState = animateColorAsState(
        targetValue =
        if (periodState.value == Week && historyPeriod == stringResource(
                id = R.string.week
            )
        ) mainColor()
        else if (periodState.value == Month && historyPeriod == stringResource(
                id = R.string.month
            )
        ) mainColor()
        else if (periodState.value == HalfOfYear && historyPeriod == stringResource(
                id = R.string.halfOfYear
            )
        ) mainColor()
        else if (periodState.value == Year && historyPeriod == stringResource(
                id =
                R.string.year
            )
        ) mainColor()
        else {
            contentBackground()
        }
    )

    Box(
        modifier = modifier
            .clip(
                shape = RoundedCornerShape(12.dp)
            )
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
            text = historyPeriod,
            fontSize = 12.sp,
            fontColor = fontColorState.value
        )
    }
}

@Composable
private fun DateItem(
    date: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .background(
                color = contentBackground()
            )
    ) {
        APText(
            modifier = Modifier
                .padding(
                    start = 12.dp
                )
                .align(CenterVertically),
            text = date,
            fontSize = 18.sp,
            fontType = FontType.Bold,
            fontColor = subTextColor()
        )
    }
}

@Composable
private fun MyComment(
    modifier: Modifier,
    title: String,
    body: String,
    date: String
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = subBackground()
            )
    ) {
        APAppendedText(
            modifier = Modifier
                .padding(
                    top = 24.dp
                )
                .padding(
                    horizontal = 12.dp
                ),
            annotatedString = buildAnnotatedString {
                withStyle(
                    style = SpanStyle(
                        color = mainTextColor()
                    )
                ) {
                    append(title)
                }

                withStyle(
                    style = SpanStyle(
                        color = subTextColor()
                    )
                ) {
                    append(" ")
                    append(
                        stringResource(
                            id = R.string.wrote_to
                        )
                    )
                }
            },
            fontSize = 14.sp,
            fontType = FontType.Bold
        )

        APText(
            modifier = Modifier
                .padding(
                    horizontal = 12.dp
                ),
            text = date,
            fontColor = subTextColor(),
            fontSize = 12.sp
        )

        Row(
            modifier = Modifier
                .padding(
                    horizontal = 12.dp,
                    vertical = 24.dp
                )
                .clip(
                    shape = RoundedCornerShape(12.dp)
                )
                .fillMaxWidth()
                .height(50.dp)
                .background(
                    color = contentBackground()
                ),
        ) {
            APText(
                modifier = Modifier
                    .padding(
                        horizontal = 12.dp
                    )
                    .align(CenterVertically),
                text = body,
                lines = 1
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
//    MyHistoryScreen(MyComment)
    MyComment(
        modifier = Modifier
            .padding(5.dp),
        title = "테스트 향수 리뷰",
        date = "2022-12-19",
        body = "테스트 내용"
    )
}