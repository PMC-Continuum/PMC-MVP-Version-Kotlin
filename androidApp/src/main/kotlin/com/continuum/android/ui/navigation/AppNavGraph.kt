package com.continuum.android.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.continuum.android.ui.screens.RoleSelectorScreen
import com.continuum.android.ui.screens.doctor.DoctorApp
import com.continuum.android.ui.screens.elderly.ElderlyApp
import com.continuum.android.ui.screens.family.FamilyApp
import com.continuum.android.ui.screens.patient.PatientApp

object Routes {
    const val ROLE_SELECTOR = "role_selector"
    const val PATIENT_APP  = "patient_app"
    const val DOCTOR_APP   = "doctor_app"
    const val FAMILY_APP   = "family_app"
    const val ELDERLY_APP  = "elderly_app"
}

@Composable
fun AppNavGraph(navController: NavHostController = rememberNavController()) {
    NavHost(
        navController = navController,
        startDestination = Routes.ROLE_SELECTOR
    ) {
        composable(Routes.ROLE_SELECTOR) {
            RoleSelectorScreen(
                onPatient  = { navController.navigate(Routes.PATIENT_APP) },
                onDoctor   = { navController.navigate(Routes.DOCTOR_APP) },
                onFamily   = { navController.navigate(Routes.FAMILY_APP) },
                onElderly  = { navController.navigate(Routes.ELDERLY_APP) }
            )
        }
        composable(Routes.PATIENT_APP) {
            PatientApp(onChangeRole = { navController.popBackStack(Routes.ROLE_SELECTOR, inclusive = false) })
        }
        composable(Routes.DOCTOR_APP) {
            DoctorApp(onChangeRole = { navController.popBackStack(Routes.ROLE_SELECTOR, inclusive = false) })
        }
        composable(Routes.FAMILY_APP) {
            FamilyApp(onChangeRole = { navController.popBackStack(Routes.ROLE_SELECTOR, inclusive = false) })
        }
        composable(Routes.ELDERLY_APP) {
            ElderlyApp(onChangeRole = { navController.popBackStack(Routes.ROLE_SELECTOR, inclusive = false) })
        }
    }
}
