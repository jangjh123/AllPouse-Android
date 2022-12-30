package com.jangjh123.allpouse_android.util

import androidx.core.app.ComponentActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


fun ioScope(codeBlock: suspend CoroutineScope.() -> Unit) =
    CoroutineScope(Dispatchers.IO).launch {
        codeBlock()
    }

fun mainScope(codeBlock: suspend CoroutineScope.() -> Unit) =
    CoroutineScope(Dispatchers.Main).launch {
        codeBlock()
    }

fun defaultScope(codeBlock: suspend CoroutineScope.() -> Unit) =
    CoroutineScope(Dispatchers.Default).launch {
        codeBlock()
    }

fun ComponentActivity.collectScope(codeBlock: suspend CoroutineScope.() -> Unit) {
    lifecycleScope.launch {
        repeatOnLifecycle(Lifecycle.State.CREATED) {
            codeBlock()
        }
    }
}