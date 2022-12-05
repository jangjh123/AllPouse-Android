package com.jangjh123.allpouse_android.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.Velocity

val HorizontalScrollConsumer = object : NestedScrollConnection {
    override fun onPreScroll(available: Offset, source: NestedScrollSource) = available.copy(y = 0f)
    override suspend fun onPreFling(available: Velocity) = available.copy(y = 0f)
}

fun Modifier.addFocusCleaner(focusManager: FocusManager) =
    this.pointerInput(Unit) {
        detectTapGestures {
            focusManager.clearFocus()
        }
    }

fun Modifier.clickableWithoutRipple(onClick: () -> Unit) = this.clickable(
    interactionSource = MutableInteractionSource(),
    indication = null
) {
    onClick()
}