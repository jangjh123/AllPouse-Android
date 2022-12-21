package com.jangjh123.allpouse_android.ui.screen.main.board

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jangjh123.allpouse_android.R
import com.jangjh123.allpouse_android.ui.component.Post
import com.jangjh123.allpouse_android.ui.component.RoundedCornerButton
import com.jangjh123.allpouse_android.ui.theme.mainColor
import com.jangjh123.allpouse_android.ui.theme.subTextColor

data class DummyPost(
    val title: String,
    val body: String,
    val authorName: String,
    val authorImage: Int,
    val hit: Int,
    val commentCount: Int,
    val imageList: List<Int>?
)

val dummyBoardNames = listOf("자유 게시판", "조향사 게시판", "정보 게시판")

@Composable
fun BoardScreen() {
    val boardState = remember { mutableStateOf(0) }

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        LazyRow(
            modifier = Modifier
                .padding(
                    horizontal = 12.dp,
                    vertical = 20.dp
                )
        ) {
            itemsIndexed(dummyBoardNames) { index, board ->
                RoundedCornerButton(
                    modifier = Modifier
                        .padding(
                            horizontal = 12.dp
                        )
                        .width(120.dp)
                        .height(40.dp),
                    text = board,
                    fontColor = if (boardState.value == index) {
                        mainColor()
                    } else {
                        subTextColor()
                    }
                ) {
                    boardState.value = index
                }
            }
        }

        LazyColumn(
            modifier = Modifier
                .padding(
                    top = 80.dp
                )
                .padding(
                    horizontal = 12.dp
                )
                .fillMaxSize()
        ) {
            items(dummyPosts) { post ->
                Post(
                    modifier = Modifier
                        .padding(
                            vertical = 4.dp
                        ),
                    post = post
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    BoardScreen()
}

val dummyPosts = listOf<DummyPost>(
    DummyPost(
        title = "테스트 포스트",
        body = "테스트 포스트 내용입니다 테스트 포스트 내용입니다 테스트 포스트 내용입니다 테스트 포스트 내용입니다 테스트 포스트 내용입니다 테스트 포스트 내용입니다 테스트 포스트 내용입니다 테스트 포스트 내용입니다 테스트 포스트 내용입니다 테스트 포스트 내용입니다 테스트 포스트 내용입니다 테스트 포스트 내용입니다 테스트 포스트 내용입니다 ",
        authorName = "테스터",
        authorImage = R.drawable.ad_banner_0,
        hit = 12,
        commentCount = 5,
        imageList = listOf(R.drawable.ad_banner_1, R.drawable.ad_banner_2)
    ),
    DummyPost(
        title = "테스트 포스트",
        body = "테스트 포스트 내용입니다",
        authorName = "테스터",
        authorImage = R.drawable.ad_banner_2,
        hit = 12,
        commentCount = 5,
        imageList = null
    ),
    DummyPost(
        title = "테스트 포스트",
        body = "테스트 포스트 내용입니다 테스트 포스트 내용입니다 테스트 포스트 내용입니다 테스트 포스트 내용입니다 테스트 포스트 내용입니다 테스트 포스트 내용입니다 테스트 포스트 내용입니다 테스트 포스트 내용입니다 테스트 포스트 내용입니다 테스트 포스트 내용입니다 테스트 포스트 내용입니다 테스트 포스트 내용입니다 테스트 포스트 내용입니다 ",
        authorName = "테스터",
        authorImage = R.drawable.ad_banner_2,
        hit = 12,
        commentCount = 5,
        imageList = listOf(R.drawable.ad_banner_0, R.drawable.ad_banner_1)
    ),
    DummyPost(
        title = "테스트 포스트",
        body = "테스트 포스트 내용입니다",
        authorName = "테스터",
        authorImage = R.drawable.ad_banner_0,
        hit = 12,
        commentCount = 5,
        imageList = null
    ),
    DummyPost(
        title = "테스트 포스트",
        body = "테스트 포스트 내용입니다 테스트 포스트 내용입니다 테스트 포스트 내용입니다 테스트 포스트 내용입니다 테스트 포스트 내용입니다 테스트 포스트 내용입니다 테스트 포스트 내용입니다 테스트 포스트 내용입니다 테스트 포스트 내용입니다 테스트 포스트 내용입니다 테스트 포스트 내용입니다 테스트 포스트 내용입니다 테스트 포스트 내용입니다 ",
        authorName = "테스터",
        authorImage = R.drawable.ad_banner_0,
        hit = 12,
        commentCount = 5,
        imageList = listOf(R.drawable.ad_banner_0, R.drawable.ad_banner_1)
    ),
    DummyPost(
        title = "테스트 포스트",
        body = "테스트 포스트 내용입니다",
        authorName = "테스터",
        authorImage = R.drawable.ad_banner_0,
        hit = 12,
        commentCount = 5,
        imageList = null
    ),
    DummyPost(
        title = "테스트 포스트",
        body = "테스트 포스트 내용입니다 테스트 포스트 내용입니다 테스트 포스트 내용입니다 테스트 포스트 내용입니다 테스트 포스트 내용입니다 테스트 포스트 내용입니다 테스트 포스트 내용입니다 테스트 포스트 내용입니다 테스트 포스트 내용입니다 테스트 포스트 내용입니다 테스트 포스트 내용입니다 테스트 포스트 내용입니다 테스트 포스트 내용입니다 ",
        authorName = "테스터",
        authorImage = R.drawable.ad_banner_0,
        hit = 12,
        commentCount = 5,
        imageList = listOf(R.drawable.ad_banner_0, R.drawable.ad_banner_1)
    ),
    DummyPost(
        title = "테스트 포스트",
        body = "테스트 포스트 내용입니다",
        authorName = "테스터",
        authorImage = R.drawable.ad_banner_0,
        hit = 12,
        commentCount = 5,
        imageList = null
    ),
    DummyPost(
        title = "테스트 포스트",
        body = "테스트 포스트 내용입니다 테스트 포스트 내용입니다 테스트 포스트 내용입니다 테스트 포스트 내용입니다 테스트 포스트 내용입니다 테스트 포스트 내용입니다 테스트 포스트 내용입니다 테스트 포스트 내용입니다 테스트 포스트 내용입니다 테스트 포스트 내용입니다 테스트 포스트 내용입니다 테스트 포스트 내용입니다 테스트 포스트 내용입니다 ",
        authorName = "테스터",
        authorImage = R.drawable.ad_banner_0,
        hit = 12,
        commentCount = 5,
        imageList = listOf(R.drawable.ad_banner_0, R.drawable.ad_banner_1)
    ),
    DummyPost(
        title = "테스트 포스트",
        body = "테스트 포스트 내용입니다",
        authorName = "테스터",
        authorImage = R.drawable.ad_banner_0,
        hit = 12,
        commentCount = 5,
        imageList = null
    )
)