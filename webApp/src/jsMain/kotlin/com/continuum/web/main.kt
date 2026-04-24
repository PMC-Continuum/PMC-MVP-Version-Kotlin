package com.continuum.web

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.CanvasBasedWindow
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import com.continuum.App
import com.continuum.presentation.navigation.DefaultRootComponent

@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    val lifecycle = LifecycleRegistry()
    val root = DefaultRootComponent(
        componentContext = DefaultComponentContext(lifecycle),
        userId = null
    )
    CanvasBasedWindow(canvasElementId = "ComposeTarget") {
        App(root)
    }
}
