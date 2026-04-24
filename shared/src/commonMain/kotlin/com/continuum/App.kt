package com.continuum

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import kotlinx.coroutines.launch
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.arkivanov.decompose.extensions.compose.stack.animation.slide
import com.arkivanov.decompose.extensions.compose.stack.animation.stackAnimation
import com.continuum.di.appModule
import com.continuum.presentation.dashboard.DashboardScreen
import com.continuum.presentation.dashboard.DashboardViewModel
import com.continuum.presentation.home.HomeScreen
import com.continuum.presentation.home.HomeViewModel
import com.continuum.presentation.navigation.DefaultRootComponent
import com.continuum.presentation.navigation.RootComponent
import com.continuum.presentation.sos.SOSScreen
import com.continuum.presentation.sos.SOSViewModel
import com.continuum.presentation.voiceentry.VoiceEntryScreen
import com.continuum.presentation.voiceentry.VoiceEntryViewModel
import org.koin.compose.KoinApplication
import org.koin.compose.koinInject

@Composable
fun App(root: DefaultRootComponent) {
    KoinApplication(application = { modules(appModule) }) {
        MaterialTheme {
            RootContent(root)
        }
    }
}

@Composable
private fun RootContent(root: DefaultRootComponent) {
    Children(
        stack = root.stack,
        animation = stackAnimation(slide())
    ) { child ->
        when (child.instance) {
            is RootComponent.Child.Landing -> {
                val supabase = org.koin.compose.koinInject<com.continuum.data.remote.SupabaseService>()
                val scope = rememberCoroutineScope()
                com.continuum.presentation.landing.LandingScreen(
                    onJoinPilot = { name, condition, city, email ->
                        scope.launch {
                            supabase.addToWaitlist(email, name, condition, city)
                        }
                    }
                )
            }
            is RootComponent.Child.Home -> {
                val vm: HomeViewModel = koinInject()
                HomeScreen(
                    viewModel = vm,
                    onNavigateToVoiceEntry = {
                        root.navigateTo(DefaultRootComponent.Config.VoiceEntry)
                    },
                    onNavigateToDashboard = {
                        root.navigateTo(DefaultRootComponent.Config.Dashboard)
                    },
                    onNavigateToSOS = {
                        root.navigateTo(DefaultRootComponent.Config.SOS)
                    }
                )
            }
            is RootComponent.Child.VoiceEntry -> {
                val vm: VoiceEntryViewModel = koinInject()
                VoiceEntryScreen(viewModel = vm, onDone = { root.goBack() })
            }
            is RootComponent.Child.Dashboard -> {
                val vm: DashboardViewModel = koinInject()
                DashboardScreen(viewModel = vm)
            }
            is RootComponent.Child.SOS -> {
                val vm: SOSViewModel = koinInject()
                SOSScreen(viewModel = vm, onBack = { root.goBack() })
            }
        }
    }
}
