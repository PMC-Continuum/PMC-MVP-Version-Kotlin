package com.continuum.android.ui.screens.patient

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import com.continuum.android.ui.theme.Red
import com.continuum.android.ui.theme.Rose
import com.continuum.android.ui.theme.T1
import com.continuum.android.ui.theme.T2
import com.continuum.android.ui.theme.T3
import com.continuum.android.ui.theme.T4
import com.continuum.android.ui.theme.Teal
import com.continuum.android.ui.theme.TealDim
import com.continuum.android.ui.animation.pulseAlphaAnimation

enum class PatientTab { Nova, Estado, Reportes, SOS }

@Composable
fun PatientApp(onChangeRole: () -> Unit) {
    var selectedTab by remember { mutableStateOf(PatientTab.Nova) }
    val snackbar = remember { SnackbarHostState() }
    var showVoice by remember { mutableStateOf(false) }

    Scaffold(
        containerColor = Bg0,
        topBar = {
            PatientTopBar(
                selectedTab = selectedTab,
                onChangeRole = onChangeRole
            )
        },
        bottomBar = {
            PatientBottomNav(
                selected = selectedTab,
                onSelect = { selectedTab = it }
            )
        },
        snackbarHost = {
            SnackbarHost(snackbar) { data -> CustomSnackbar(data) }
        }
    ) { padding ->
        when (selectedTab) {
            PatientTab.Nova     -> NovaChatScreen(
                modifier = Modifier.padding(padding),
                snackbar = snackbar,
                onMicTap = { showVoice = true }
            )
            PatientTab.Estado   -> PatientEstadoScreen(Modifier.padding(padding), snackbar)
            PatientTab.Reportes -> PatientReportesScreen(Modifier.padding(padding), snackbar)
            PatientTab.SOS      -> PatientSOSScreen(Modifier.padding(padding), snackbar)
        }
    }

    if (showVoice) {
        VoiceOverlayScreen(onDismiss = { showVoice = false })
    }
}

@Composable
fun PatientTopBar(selectedTab: PatientTab, onChangeRole: () -> Unit) {
    val title = when (selectedTab) {
        PatientTab.Nova     -> "Nova"
        PatientTab.Estado   -> "Mi estado"
        PatientTab.Reportes -> "Reportes"
        PatientTab.SOS      -> "SOS"
    }
    val dotAlpha by pulseAlphaAnimation()

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Bg1)
            .border(width = 1.dp, color = B1, shape = RoundedCornerShape(0.dp))
            .padding(horizontal = 18.dp, vertical = 0.dp)
            .height(54.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(11.dp)
    ) {
        Box(
            modifier = Modifier
                .size(32.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(Brush.linearGradient(listOf(Teal, BlueAccent))),
            contentAlignment = Alignment.Center
        ) {
            Text("∞", color = Color.White, fontSize = 14.sp, fontWeight = FontWeight.Bold)
        }
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleSmall.copy(
                    fontFamily = OutfitFamily,
                    fontWeight = FontWeight.Bold,
                    color = T1,
                    fontSize = 14.sp
                )
            )
            Text(
                text = "Carlos Méndez · DM2 + HTA",
                style = MaterialTheme.typography.labelSmall.copy(color = T3, fontSize = 10.5.sp)
            )
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(6.dp)
                    .clip(CircleShape)
                    .background(Teal.copy(alpha = dotAlpha))
            )
            Text(
                text = "Activo",
                style = MaterialTheme.typography.labelSmall.copy(
                    color = Teal,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 10.5.sp
                )
            )
        }
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(20.dp))
                .background(Rose)
                .padding(horizontal = 7.dp, vertical = 2.dp)
        ) {
            Text(
                text = "2",
                style = MaterialTheme.typography.labelSmall.copy(
                    color = Color.White,
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 9.5.sp
                )
            )
        }
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(8.dp))
                .border(1.dp, B2, RoundedCornerShape(8.dp))
                .clickable { onChangeRole() }
                .padding(horizontal = 11.dp, vertical = 5.dp)
        ) {
            Text(
                text = "Rol",
                style = MaterialTheme.typography.labelSmall.copy(color = T2, fontSize = 11.sp)
            )
        }
        Box(
            modifier = Modifier
                .size(28.dp)
                .clip(CircleShape)
                .background(Brush.linearGradient(listOf(Teal, BlueAccent))),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "CM",
                style = MaterialTheme.typography.labelSmall.copy(
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 10.5.sp
                )
            )
        }
    }
}

@Composable
fun PatientBottomNav(selected: PatientTab, onSelect: (PatientTab) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Bg1)
            .border(width = 1.dp, color = B1, shape = RoundedCornerShape(0.dp))
            .padding(horizontal = 10.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        PatientTab.values().forEach { tab ->
            val isActive = selected == tab
            val isSOS = tab == PatientTab.SOS
            val tabColor by animateColorAsState(
                targetValue = when {
                    isSOS   -> if (isActive) Red else Red.copy(alpha = 0.7f)
                    isActive -> Teal
                    else     -> T4
                },
                animationSpec = tween(160),
                label = "tabColor"
            )
            val bgColor by animateColorAsState(
                targetValue = when {
                    isSOS && isActive -> Red.copy(alpha = 0.1f)
                    isActive -> TealDim
                    else -> Color.Transparent
                },
                animationSpec = tween(160),
                label = "tabBg"
            )
            Column(
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(12.dp))
                    .background(bgColor)
                    .clickable { onSelect(tab) }
                    .padding(vertical = 8.dp, horizontal = 6.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = when (tab) {
                        PatientTab.Nova     -> "💬"
                        PatientTab.Estado   -> "📊"
                        PatientTab.Reportes -> "📋"
                        PatientTab.SOS      -> "🚨"
                    },
                    fontSize = 20.sp
                )
                Text(
                    text = when (tab) {
                        PatientTab.Nova     -> "Nova"
                        PatientTab.Estado   -> "Mi estado"
                        PatientTab.Reportes -> "Reportes"
                        PatientTab.SOS      -> "SOS"
                    },
                    style = MaterialTheme.typography.labelSmall.copy(
                        color = tabColor,
                        fontFamily = DmSansFamily,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 10.5.sp
                    )
                )
            }
        }
    }
}
