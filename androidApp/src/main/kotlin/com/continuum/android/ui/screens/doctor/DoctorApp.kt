package com.continuum.android.ui.screens.doctor

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.continuum.android.ui.components.CustomSnackbar
import com.continuum.android.ui.theme.B1
import com.continuum.android.ui.theme.B2
import com.continuum.android.ui.theme.Bg0
import com.continuum.android.ui.theme.Bg1
import com.continuum.android.ui.theme.BlueAccent
import com.continuum.android.ui.theme.DmSansFamily
import com.continuum.android.ui.theme.OutfitFamily
import com.continuum.android.ui.theme.PurpleRole
import com.continuum.android.ui.theme.T1
import com.continuum.android.ui.theme.T2
import com.continuum.android.ui.theme.T3
import com.continuum.android.ui.theme.T4
import com.continuum.android.ui.theme.Teal
import com.continuum.android.ui.theme.TealDim
import kotlinx.coroutines.launch

enum class DoctorTab { Patients, Alerts, Agenda, Trends }

@Composable
fun DoctorApp(onChangeRole: () -> Unit) {
    var selectedTab by remember { mutableStateOf(DoctorTab.Patients) }
    var showDetail by remember { mutableStateOf(false) }
    val snackbar = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    if (showDetail) {
        DoctorPatientDetailScreen(onBack = { showDetail = false }, snackbar = snackbar)
        return
    }

    Scaffold(
        containerColor = Bg0,
        topBar = { DoctorTopBar(onChangeRole = onChangeRole) },
        bottomBar = { DoctorBottomNav(selected = selectedTab, onSelect = { tab ->
            selectedTab = tab
            if (tab != DoctorTab.Patients) {
                scope.launch {
                    when (tab) {
                        DoctorTab.Alerts  -> snackbar.showSnackbar("⚠️ 2 alertas activas: Lucía y Carlos")
                        DoctorTab.Agenda  -> snackbar.showSnackbar("📅 Hoy: 3 citas · próxima a las 10:30 am")
                        DoctorTab.Trends  -> snackbar.showSnackbar("📊 Glucosa promedio panel: 168 mg/dL ↑")
                        else -> {}
                    }
                }
            }
        }) },
        snackbarHost = { SnackbarHost(snackbar) { data -> CustomSnackbar(data) } }
    ) { padding ->
        DoctorPatientListScreen(
            modifier = Modifier.padding(padding),
            onPatientClick = { showDetail = true },
            snackbar = snackbar
        )
    }
}

@Composable
private fun DoctorTopBar(onChangeRole: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Bg1)
            .border(1.dp, B1, RoundedCornerShape(0.dp))
            .padding(horizontal = 18.dp)
            .height(54.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(11.dp)
    ) {
        Box(
            modifier = Modifier.size(32.dp).clip(RoundedCornerShape(10.dp)).background(Brush.linearGradient(listOf(Teal, BlueAccent))),
            contentAlignment = Alignment.Center
        ) { Text("∞", color = Color.White, fontSize = 14.sp, fontWeight = FontWeight.Bold) }
        Column(modifier = Modifier.weight(1f)) {
            Text("Mis pacientes", style = MaterialTheme.typography.titleSmall.copy(fontFamily = OutfitFamily, fontWeight = FontWeight.Bold, color = T1, fontSize = 14.sp))
            Text("Dra. Adriana Peña · Endocrinología", style = MaterialTheme.typography.labelSmall.copy(color = T3, fontSize = 10.5.sp))
        }
        Box(Modifier.clip(RoundedCornerShape(20.dp)).background(PurpleRole.copy(alpha = 0.9f)).padding(7.dp, 2.dp)) {
            Text("2 urgentes", style = MaterialTheme.typography.labelSmall.copy(color = Color.White, fontWeight = FontWeight.Bold, fontSize = 9.5.sp))
        }
        Box(Modifier.clip(RoundedCornerShape(8.dp)).border(1.dp, B2, RoundedCornerShape(8.dp)).clickable { onChangeRole() }.padding(11.dp, 5.dp)) {
            Text("Rol", style = MaterialTheme.typography.labelSmall.copy(color = T2, fontSize = 11.sp))
        }
        Box(Modifier.size(28.dp).clip(CircleShape).background(Brush.linearGradient(listOf(PurpleRole, Color(0xFF7C3AED)))), contentAlignment = Alignment.Center) {
            Text("AP", style = MaterialTheme.typography.labelSmall.copy(color = Color.White, fontWeight = FontWeight.Bold, fontSize = 10.5.sp))
        }
    }
}

@Composable
private fun DoctorBottomNav(selected: DoctorTab, onSelect: (DoctorTab) -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth().background(Bg1).border(1.dp, B1, RoundedCornerShape(0.dp)).padding(10.dp, 8.dp),
        horizontalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        DoctorTab.values().forEach { tab ->
            val isActive = selected == tab
            val tabColor by animateColorAsState(if (isActive) Teal else T4, tween(160), label = "tc")
            val bgColor by animateColorAsState(if (isActive) TealDim else Color.Transparent, tween(160), label = "bg")
            Column(
                modifier = Modifier.weight(1f).clip(RoundedCornerShape(12.dp)).background(bgColor).clickable { onSelect(tab) }.padding(vertical = 8.dp, horizontal = 6.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(when (tab) { DoctorTab.Patients -> "👥"; DoctorTab.Alerts -> "🔔"; DoctorTab.Agenda -> "📅"; DoctorTab.Trends -> "📈" }, fontSize = 20.sp)
                Text(when (tab) { DoctorTab.Patients -> "Pacientes"; DoctorTab.Alerts -> "Alertas"; DoctorTab.Agenda -> "Agenda"; DoctorTab.Trends -> "Tendencias" },
                    style = MaterialTheme.typography.labelSmall.copy(color = tabColor, fontFamily = DmSansFamily, fontWeight = FontWeight.SemiBold, fontSize = 10.5.sp))
            }
        }
    }
}
