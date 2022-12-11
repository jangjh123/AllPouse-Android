package com.jangjh123.allpouse_android.ui.screen.my_info

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterEnd
import androidx.compose.ui.Alignment.Companion.CenterStart
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jangjh123.allpouse_android.R
import com.jangjh123.allpouse_android.ui.component.APText
import com.jangjh123.allpouse_android.ui.component.clickableWithoutRipple
import com.jangjh123.allpouse_android.ui.theme.subBackground
import com.jangjh123.allpouse_android.ui.theme.subTextColor

@Composable
fun MyInfoScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .background(
                    color = subBackground()
                )
                .padding(
                    all = 24.dp
                )
        ) {
            Row(
                modifier = Modifier
                    .background(
                        color = subBackground()
                    )
            ) {
                Image(
                    modifier = Modifier
                        .clip(shape = CircleShape)
                        .size(72.dp)
                        .align(CenterVertically),
                    painter = painterResource(id = R.drawable.ad_banner_0),
                    contentDescription = "profileImage",
                    contentScale = ContentScale.FillBounds
                )

                Column(
                    modifier = Modifier
                        .padding(start = 12.dp)
                        .align(CenterVertically)
                ) {
                    APText(
                        text = "지호",
                        fontSize = 24.sp
                    )
                    APText(
                        text = "20대 남성",
                        fontSize = 12.sp,
                        fontColor = subTextColor()
                    )
                }
            }

            Icon(
                modifier = Modifier
                    .clickableWithoutRipple {

                    }
                    .size(24.dp)
                    .align(CenterEnd),
                painter = painterResource(id = R.drawable.ic_settings),
                contentDescription = "settingsIcon",
                tint = subTextColor()
            )
        }


        Column {
            SettingsItem(
                text = stringResource(
                    id = R.string.taste_keyword
                ),
                icon = painterResource(
                    id = R.drawable.ic_keyword
                )
            ) {

            }

            Divider(
                modifier = Modifier
                    .padding(horizontal = 12.dp),
                color = subBackground()
            )

            SettingsItem(
                text = stringResource(
                    id = R.string.my_like
                ),
                icon = painterResource(
                    id = R.drawable.ic_filled_heart
                )
            ) {

            }

            Divider(
                modifier = Modifier
                    .padding(horizontal = 12.dp),
                color = subBackground()
            )

            SettingsItem(
                text = stringResource(
                    id = R.string.my_review
                ),
                icon = painterResource(
                    id = R.drawable.ic_review
                )
            ) {

            }

            Divider(
                modifier = Modifier
                    .padding(horizontal = 12.dp),
                color = subBackground()
            )

            SettingsItem(
                text = stringResource(
                    id = R.string.my_comment
                ),
                icon = painterResource(
                    id = R.drawable.ic_comment
                )
            ) {

            }

            Divider(
                modifier = Modifier
                    .padding(horizontal = 12.dp),
                color = subBackground()
            )

            SettingsItem(
                text = stringResource(
                    id = R.string.perfumer_settings
                ),
                icon = painterResource(
                    id = R.drawable.ic_perfumer
                )
            ) {

            }

            Divider(
                modifier = Modifier
                    .padding(horizontal = 12.dp),
                color = subBackground()
            )
        }
    }
}

@Composable
private fun SettingsItem(
    text: String,
    icon: Painter,
    onClick: () -> Unit
) {
    Box(modifier = Modifier
        .clickable {
            onClick()
        }
        .padding(start = 24.dp)
        .padding(vertical = 8.dp)
        .fillMaxWidth()
        .height(60.dp)
    ) {
        Row(
            modifier = Modifier
                .align(CenterStart)
        ) {
            Icon(
                modifier = Modifier
                    .size(24.dp)
                    .align(CenterVertically),
                painter = icon,
                contentDescription = "menuItemIcon",
                tint = subTextColor()
            )

            APText(
                modifier = Modifier
                    .padding(start = 24.dp)
                    .align(CenterVertically),
                text = text,
                fontSize = 16.sp
            )
        }

        Icon(
            modifier = Modifier
                .padding(
                    end = 24.dp
                )
                .size(16.dp)
                .align(CenterEnd),
            painter = painterResource(
                id = R.drawable.arrow_right
            ),
            contentDescription = "arrow"
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
//    MyInfoScreen()
}

@Preview(showBackground = true)
@Composable
private fun SettingsItemPreview() {
    SettingsItem(text = "옵션", icon = painterResource(id = R.drawable.ic_settings)) {
    }
}