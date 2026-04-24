package com.continuum.web

import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.CanvasBasedWindow
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import com.continuum.data.remote.SupabaseService
import com.continuum.presentation.landing.LandingScreen
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

// onWasmReady is a WasmJS-only API — not available in js(IR).
// CanvasBasedWindow handles Skiko WASM initialization internally in CMP 1.7.
@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    CanvasBasedWindow(canvasElementId = "ComposeTarget") {
        val lifecycle = remember { LifecycleRegistry() }
        val supabase = remember { SupabaseService() }
        val scope = remember { MainScope() }

        LandingScreen(
            onJoinPilot = { name, condition, city, email ->
                scope.launch {
                    supabase.addToWaitlist(email, name, condition, city)
                }
            }
        )
    }
}
