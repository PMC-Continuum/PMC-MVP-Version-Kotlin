package com.continuum

import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.arkivanov.decompose.extensions.compose.stack.animation.slide
import com.arkivanov.decompose.extensions.compose.stack.animation.stackAnimation
import com.continuum.presentation.navigation.DefaultRootComponent
import com.continuum.presentation.navigation.RootComponent
import com.continuum.ui.screens.RoleSelectorScreen
import com.continuum.ui.screens.doctor.DoctorApp
import com.continuum.ui.screens.elderly.ElderlyApp
import com.continuum.ui.screens.family.FamilyApp
import com.continuum.ui.screens.patient.PatientApp
import com.continuum.ui.theme.ContinuumTheme
import kotlinx.coroutines.launch

@Composable
fun App(root: DefaultRootComponent) {
    ContinuumTheme {
        RootContent(root)
    }
}

@Composable
private fun RootContent(root: DefaultRootComponent) {
    val scope = rememberCoroutineScope()
    Children(
        stack = root.stack,
        animation = stackAnimation(slide())
    ) { child ->
        when (child.instance) {
            is RootComponent.Child.RoleSelector -> {
                RoleSelectorScreen(
                    onPatient = { root.navigateTo(DefaultRootComponent.Config.PatientApp) },
                    onDoctor  = { root.navigateTo(DefaultRootComponent.Config.DoctorApp) },
                    onFamily  = { root.navigateTo(DefaultRootComponent.Config.FamilyApp) },
                    onElderly = { root.navigateTo(DefaultRootComponent.Config.ElderlyApp) }
                )
            }
            is RootComponent.Child.PatientApp -> {
                PatientApp(onChangeRole = { root.replaceWith(DefaultRootComponent.Config.RoleSelector) })
            }
            is RootComponent.Child.DoctorApp -> {
                DoctorApp(onChangeRole = { root.replaceWith(DefaultRootComponent.Config.RoleSelector) })
            }
            is RootComponent.Child.FamilyApp -> {
                FamilyApp(onChangeRole = { root.replaceWith(DefaultRootComponent.Config.RoleSelector) })
            }
            is RootComponent.Child.ElderlyApp -> {
                ElderlyApp(onChangeRole = { root.replaceWith(DefaultRootComponent.Config.RoleSelector) })
            }
            is RootComponent.Child.Landing -> {
                val supabase = org.koin.compose.koinInject<com.continuum.data.remote.SupabaseService>()
                com.continuum.presentation.landing.LandingScreen(
                    onJoinPilot = { name, condition, city, email ->
                        scope.launch { supabase.addToWaitlist(email, name, condition, city) }
                    }
                )
            }
            is RootComponent.Child.Home,
            is RootComponent.Child.VoiceEntry,
            is RootComponent.Child.Dashboard,
            is RootComponent.Child.SOS -> {}
        }
    }
}
