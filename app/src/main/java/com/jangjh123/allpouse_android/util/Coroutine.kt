package com.jangjh123.allpouse_android.util

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

object Coroutine {
    fun io(codeBlock: suspend CoroutineScope.() -> Unit) =
        CoroutineScope(Dispatchers.IO).launch {
            codeBlock()
        }

    fun main(codeBlock: suspend CoroutineScope.() -> Unit) =
        CoroutineScope(Dispatchers.Main).launch {
            codeBlock()
        }

    fun default(codeBlock: suspend CoroutineScope.() -> Unit) =
        CoroutineScope(Dispatchers.Default).launch {
            codeBlock()
        }
}