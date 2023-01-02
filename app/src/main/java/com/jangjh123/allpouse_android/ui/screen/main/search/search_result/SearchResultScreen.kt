package com.jangjh123.allpouse_android.ui.screen.main.search.search_result

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jangjh123.allpouse_android.R
import com.jangjh123.allpouse_android.ui.component.*
import com.jangjh123.allpouse_android.ui.screen.main.home.PERFUME_ITEM_HEIGHT
import com.jangjh123.allpouse_android.ui.screen.main.home.dummyPerfumesForYou
import com.jangjh123.allpouse_android.ui.theme.mainTextColor
import com.jangjh123.allpouse_android.ui.theme.subBackground
import com.jangjh123.allpouse_android.ui.theme.subTextColor

@Composable
fun SearchResultScreen() {

//    no data
//    Box(
//        modifier = Modifier
//            .fillMaxSize()
//    ) {
//        Column(
//            modifier = Modifier
//                .align(Alignment.Center)
//        ) {
//            Icon(
//                modifier = Modifier
//                    .size(36.dp)
//                    .align(CenterHorizontally),
//                painter = painterResource(
//                    id = R.drawable.ic_no_result
//                ),
//                contentDescription = "noResultIcon",
//                tint = subTextColor()
//            )
//
//            APText(
//                modifier = Modifier
//                    .padding(
//                        top = 8.dp
//                    ),
//                text = stringResource(
//                    id = R.string.no_search_result
//                ),
//                fontSize = 20.sp,
//                fontColor = subTextColor()
//            )
//
//            RoundedCornerButton(
//                modifier = Modifier
//                    .padding(
//                        top = 36.dp
//                    )
//                    .width(120.dp)
//                    .height(50.dp)
//                    .align(
//                        CenterHorizontally
//                    ),
//                text = stringResource(
//                    id = R.string.go_back
//                ),
//                fontColor = subTextColor()
//            ) {
//
//            }
//        }
//    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
    ) {
        item {
            APText(
                modifier = Modifier
                    .padding(
                        start = 12.dp,
                        top = 24.dp
                    ),
                text = stringResource(
                    id = R.string.search_result_count,
                    "향수",
                    "32"
                ),
                fontSize = 20.sp,
                fontType = FontType.Bold
            )
        }

        item {
            SearchResultType(
                type = stringResource(
                    id = R.string.perfume
                ),
                count = 132
            )
        }

        item {
            LazyRow(
                modifier = Modifier
                    .padding(
                        horizontal = 8.dp
                    )
            ) {
//                items(dummyPerfumesForYou) { perfume ->
//                    Perfume(
//                        modifier = Modifier
//                            .padding(
//                                horizontal = 4.dp
//                            )
//                            .clip(
//                                shape = RoundedCornerShape(12.dp)
//                            )
//                            .background(
//                                color = subBackground()
//                            ),
//                        perfumeName = perfume.perfumeName,
//                        brandName = perfume.brandName,
//                        image = painterResource(
//                            id = perfume.image
//                        ),
//                        keywordCount = perfume.keywordCount
//                    )
//                }

                item {
                    Box(
                        modifier = Modifier
                            .width(136.dp)
                            .height(PERFUME_ITEM_HEIGHT)
                    ) {
                        Column(
                            modifier = Modifier
                                .align(Alignment.Center)
                                .wrapContentSize()
                        ) {
                            Icon(
                                modifier = Modifier
                                    .size(36.dp)
                                    .align(Alignment.CenterHorizontally),
                                painter = painterResource(
                                    id = R.drawable.ic_arrow_foward
                                ),
                                tint = subTextColor(),
                                contentDescription = "goForMorePerfumes"
                            )

                            APText(
                                modifier = Modifier
                                    .padding(4.dp),
                                text = stringResource(
                                    id = R.string.show_more
                                ),
                                fontColor = subTextColor()
                            )
                        }
                    }
                }
            }
        }

        item {
            SearchResultType(
                type = stringResource(
                    id = R.string.review
                ),
                count = 132
            )
        }

        item {
            Column(
                modifier = Modifier
                    .padding(
                        horizontal = 12.dp
                    )
            ) {
                Review(
                    modifier = Modifier
                        .padding(
                            vertical = 4.dp
                        ),
                    score = 4.32f,
                    perfumeName = "TestPerfume",
                    image = painterResource(
                        id = R.drawable.perfume_test_1
                    ),
                    title = "Test Title",
                    body = "Test Body",
                    author = "Test Author",
                    authorImage = painterResource(
                        id = R.drawable.ad_banner_2
                    ),
                    hit = 6554,
                    recommend = 32
                )

                Review(
                    modifier = Modifier
                        .padding(
                            vertical = 4.dp
                        ),
                    score = 4.32f,
                    perfumeName = "TestPerfume",
                    image = painterResource(
                        id = R.drawable.perfume_test_1
                    ),
                    title = "Test Title",
                    body = "Test Body",
                    author = "Test Author",
                    authorImage = painterResource(
                        id = R.drawable.ad_banner_2
                    ),
                    hit = 6554,
                    recommend = 32
                )

                Review(
                    modifier = Modifier
                        .padding(
                            vertical = 4.dp
                        ),
                    score = 4.32f,
                    perfumeName = "TestPerfume",
                    image = painterResource(
                        id = R.drawable.perfume_test_1
                    ),
                    title = "Test Title",
                    body = "Test Body",
                    author = "Test Author",
                    authorImage = painterResource(
                        id = R.drawable.ad_banner_2
                    ),
                    hit = 6554,
                    recommend = 32
                )

                RoundedCornerButton(
                    modifier = Modifier
                        .padding(
                            top = 24.dp
                        )
                        .fillMaxWidth()
                        .height(50.dp),
                    text = stringResource(
                        id = R.string.show_more_review
                    )
                ) {

                }
            }
        }

        item {
            SearchResultType(
                type = stringResource(
                    id = R.string.post
                ),
                count = 132
            )
        }

        item {
            Column(
                modifier = Modifier
                    .padding(
                        horizontal = 12.dp
                    )
            ) {
                PostWithBoardName(
                    modifier = Modifier
                        .padding(
                            vertical = 4.dp
                        ),
                    board = "조향사게시판",
                    postName = "테스트 포스트",
                    like = 12
                )

                PostWithBoardName(
                    modifier = Modifier
                        .padding(
                            vertical = 4.dp
                        ),
                    board = "조향사게시판",
                    postName = "테스트 포스트",
                    like = 12
                )

                PostWithBoardName(
                    modifier = Modifier
                        .padding(
                            vertical = 4.dp
                        ),
                    board = "조향사게시판",
                    postName = "테스트 포스트",
                    like = 12
                )

                PostWithBoardName(
                    modifier = Modifier
                        .padding(
                            vertical = 4.dp
                        ),
                    board = "조향사게시판",
                    postName = "테스트 포스트",
                    like = 12
                )

                PostWithBoardName(
                    modifier = Modifier
                        .padding(
                            vertical = 4.dp
                        ),
                    board = "조향사게시판",
                    postName = "테스트 포스트",
                    like = 12
                )

                RoundedCornerButton(
                    modifier = Modifier
                        .padding(
                            vertical = 24.dp
                        )
                        .fillMaxWidth()
                        .height(50.dp),
                    text = stringResource(
                        id = R.string.show_more_post
                    )
                ) {

                }
            }
        }
    }
}

@Composable
private fun SearchResultType(type: String, count: Int) {
    APAppendedText(
        modifier = Modifier
            .padding(
                start = 12.dp,
                top = 36.dp,
                bottom = 12.dp
            ),
        annotatedString = buildAnnotatedString {
            withStyle(
                style = SpanStyle(
                    color = mainTextColor()
                )
            ) {
                append(type)
            }

            withStyle(
                style = SpanStyle(
                    color = subTextColor()
                )
            ) {
                append(" ")
                append("${count}건") // ###,###,###
            }
        },
        fontSize = 20.sp,
        fontType = FontType.Bold
    )
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    SearchResultScreen()
}