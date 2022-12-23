package com.jangjh123.allpouse_android.ui.screen.detail.brand_detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jangjh123.allpouse_android.R
import com.jangjh123.allpouse_android.ui.component.*
import com.jangjh123.allpouse_android.ui.screen.main.home.dummyPerfumesForYou
import com.jangjh123.allpouse_android.ui.theme.background
import com.jangjh123.allpouse_android.ui.theme.brandLogoBackground

@Composable
fun BrandDetailScreen() {
    val scrollState = rememberScrollState()
    val testBrandName = "Versace"

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                color = Color.White
            )
            .verticalScroll(
                state = scrollState
            )
            .background(
                color = brandLogoBackground()
            )
    ) {
        Image(
            modifier = Modifier
                .padding(24.dp)
                .fillMaxWidth()
                .height(120.dp),
            painter = painterResource(
                id = R.drawable.brand_test_0
            ),
            contentDescription = "brandLogoImage",
            contentScale = ContentScale.Inside
        )

        Column(
            modifier = Modifier
                .clip(
                    shape = RoundedCornerShape(
                        topStart = 24.dp,
                        topEnd = 24.dp
                    )
                )
                .fillMaxSize()
                .background(
                    color = background()
                )
        ) {

            APText(
                modifier = Modifier
                    .padding(
                        top = 36.dp,
                        start = 12.dp
                    ),
                text = stringResource(
                    id = R.string.brand_popular_perfumes, testBrandName
                ),
                fontSize = 20.sp,
                fontType = FontType.Bold
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                with(dummyPerfumesForYou) {
                    Perfume(
                        modifier = Modifier
                            .padding(
                                start = 12.dp,
                                end = 6.dp
                            )
                            .padding(
                                vertical = 12.dp
                            )
                            .weight(0.5f),
                        perfumeName = get(0).perfumeName,
                        brandName = get(0).brandName,
                        image = painterResource(
                            id = get(0).image
                        ),
                        keywordCount = get(0).keywordCount
                    )

                    Perfume(
                        modifier = Modifier
                            .padding(
                                start = 6.dp,
                                end = 12.dp
                            )
                            .padding(
                                vertical = 12.dp
                            )
                            .weight(0.5f),
                        perfumeName = get(1).perfumeName,
                        brandName = get(1).brandName,
                        image = painterResource(
                            id = get(1).image
                        ),
                        keywordCount = get(1).keywordCount
                    )
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                with(dummyPerfumesForYou) {
                    Perfume(
                        modifier = Modifier
                            .padding(
                                start = 12.dp,
                                end = 6.dp
                            )
                            .weight(0.5f),
                        perfumeName = get(2).perfumeName,
                        brandName = get(2).brandName,
                        image = painterResource(
                            id = get(2).image
                        ),
                        keywordCount = get(2).keywordCount
                    )

                    Perfume(
                        modifier = Modifier
                            .padding(
                                start = 6.dp,
                                end = 12.dp
                            )
                            .weight(0.5f),
                        perfumeName = get(3).perfumeName,
                        brandName = get(3).brandName,
                        image = painterResource(
                            id = get(3).image
                        ),
                        keywordCount = get(3).keywordCount
                    )
                }
            }

            RoundedCornerButton(
                modifier = Modifier
                    .padding(
                        top = 12.dp
                    )
                    .padding(
                        horizontal = 12.dp
                    )
                    .fillMaxWidth()
                    .height(50.dp),
                text = stringResource(
                    id = R.string.show_more_brand_perfumes, testBrandName
                )
            ) {

            }

            Spacer(
                modifier = Modifier
                    .height(12.dp)
            )

            APText(
                modifier = Modifier
                    .padding(
                        top = 36.dp,
                        bottom = 12.dp
                    )
                    .padding(
                        horizontal = 12.dp
                    ),
                text = stringResource(
                    id = R.string.brand_perfume_reviews, testBrandName
                ),
                fontSize = 20.sp,
                fontType = FontType.Bold
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Review(
                    modifier = Modifier
                        .padding(
                            bottom = 6.dp
                        )
                        .padding(
                            horizontal = 12.dp
                        ),
                    score = 4.7f,
                    perfumeName = "TestPerfume",
                    image = painterResource(
                        id = R.drawable.ad_banner_1
                    ),
                    title = "테스트 향수입니다.",
                    body = "사람들의 내 내 봅니다. 까닭이요, 벌레는 나는 듯합니다. 아무 우는 사람들의 잠, 다 별이 이름을 까닭입니다. 소녀들의 새겨지는 않은 하늘에는 버리었습니다. 이름과, 하나의 벌써 토끼, 새겨지는 별이 그리고 것은 없이 있습니다. 했던 위에 아름다운 덮어 밤을 그러나 이름과 까닭이요, 봅니다. 이름자를 어머니, 위에 별 나의 것은 계절이 버리었습니다. 나는 써 하나에 그리고 동경과 가을로 멀듯이, 계십니다. 위에 이네들은 가득 까닭입니다. 못 피어나듯이 아름다운 부끄러운 지나가는 잠, 봅니다. 이름과, 가을 별 아름다운 흙으로 별빛이 봅니다.",
                    author = "TestAuthor1",
                    authorImage = painterResource(
                        id = R.drawable.ad_banner_1
                    ),
                    hit = 3312,
                    recommend = 43
                )

                Review(
                    modifier = Modifier
                        .padding(
                            horizontal = 12.dp,
                            vertical = 6.dp
                        ),
                    score = 4.7f,
                    perfumeName = "TestPerfume",
                    image = painterResource(
                        id = R.drawable.ad_banner_1
                    ),
                    title = "테스트 향수입니다.",
                    body = "사람들의 내 내 봅니다. 까닭이요, 벌레는 나는 듯합니다. 아무 우는 사람들의 잠, 다 별이 이름을 까닭입니다. 소녀들의 새겨지는 않은 하늘에는 버리었습니다. 이름과, 하나의 벌써 토끼, 새겨지는 별이 그리고 것은 없이 있습니다. 했던 위에 아름다운 덮어 밤을 그러나 이름과 까닭이요, 봅니다. 이름자를 어머니, 위에 별 나의 것은 계절이 버리었습니다. 나는 써 하나에 그리고 동경과 가을로 멀듯이, 계십니다. 위에 이네들은 가득 까닭입니다. 못 피어나듯이 아름다운 부끄러운 지나가는 잠, 봅니다. 이름과, 가을 별 아름다운 흙으로 별빛이 봅니다.",
                    author = "TestAuthor1",
                    authorImage = painterResource(
                        id = R.drawable.ad_banner_1
                    ),
                    hit = 3312,
                    recommend = 43
                )

                Review(
                    modifier = Modifier
                        .padding(
                            top = 6.dp
                        )
                        .padding(
                            horizontal = 12.dp
                        ),
                    score = 4.7f,
                    perfumeName = "TestPerfume",
                    image = painterResource(
                        id = R.drawable.ad_banner_1
                    ),
                    title = "테스트 향수입니다.",
                    body = "사람들의 내 내 봅니다. 까닭이요, 벌레는 나는 듯합니다. 아무 우는 사람들의 잠, 다 별이 이름을 까닭입니다. 소녀들의 새겨지는 않은 하늘에는 버리었습니다. 이름과, 하나의 벌써 토끼, 새겨지는 별이 그리고 것은 없이 있습니다. 했던 위에 아름다운 덮어 밤을 그러나 이름과 까닭이요, 봅니다. 이름자를 어머니, 위에 별 나의 것은 계절이 버리었습니다. 나는 써 하나에 그리고 동경과 가을로 멀듯이, 계십니다. 위에 이네들은 가득 까닭입니다. 못 피어나듯이 아름다운 부끄러운 지나가는 잠, 봅니다. 이름과, 가을 별 아름다운 흙으로 별빛이 봅니다.",
                    author = "TestAuthor1",
                    authorImage = painterResource(
                        id = R.drawable.ad_banner_1
                    ),
                    hit = 3312,
                    recommend = 43
                )

                RoundedCornerButton(
                    modifier = Modifier
                        .padding(
                            vertical = 12.dp,
                            horizontal = 12.dp
                        )
                        .fillMaxWidth()
                        .height(50.dp),
                    text = stringResource(
                        id = R.string.show_more_brand_perfume_reviews, testBrandName
                    )
                ) {

                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    BrandDetailScreen()
}