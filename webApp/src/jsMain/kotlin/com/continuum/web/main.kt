package com.continuum.web

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.CanvasBasedWindow
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import com.continuum.App
import com.continuum.data.remote.SupabaseService
import com.continuum.presentation.landing.LandingScreen
import com.continuum.presentation.navigation.DefaultRootComponent
import androidx.compose.runtime.remember
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import org.jetbrains.skiko.wasm.onWasmReady

@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    onWasmReady {
        CanvasBasedWindow(canvasElementId = "ComposeTarget") {
            val lifecycle = remember { LifecycleRegistry() }
            val supabase = remember { SupabaseService() }
            val scope = remember { MainScope() }

            // Show landing page directly on web
            LandingScreen(
                onJoinPilot = { name, condition, city, email ->
                    scope.launch {
                        supabase.addToWaitlist(email, name, condition, city)
                    }
                }
            )
        }
    }
}
