package com.continuum.ui.screens.family

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.continuum.ui.theme.Amber
import com.continuum.ui.theme.B1
import com.continuum.ui.theme.B2
import com.continuum.ui.theme.Bg0
import com.continuum.ui.theme.Bg1
import com.continuum.ui.theme.Bg2
import com.continuum.ui.theme.BlueAccent
import com.continuum.ui.theme.DmSansFamily
import com.continuum.ui.theme.Green
import com.continuum.ui.theme.LargeRadius
import com.continuum.ui.theme.MedRadius
import com.continuum.ui.theme.OutfitFamily
import com.continuum.ui.theme.T1
import com.continuum.ui.theme.T2
import com.continuum.ui.theme.T3
import com.continuum.ui.theme.T4
import com.continuum.ui.theme.Teal
import com.continuum.ui.theme.TealDim
import kotlinx.coroutines.launch

enum class FamilyTab { Overview, Meds, Reports }

@Composable
fun FamilyApp(onChangeRole: () -> Unit) {
    var selectedTab by remember { mutableStateOf(FamilyTab.Overview) }
    val snackbar = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    Scaffold(
        containerColor = Bg0,
        topBar = {
            Row(Modifier.fillMaxWidth().background(Bg1).border(1.dp, B1, RoundedCornerShape(0.dp)).padding(horizontal = 18.dp).height(54.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(11.dp)) {
                Box(Modifier.size(32.dp).clip(RoundedCornerShape(10.dp)).background(Brush.linearGradient(listOf(Teal, BlueAccent))), contentAlignment = Alignment.Center) { Text("∞", color = Color.White, fontSize = 14.sp, fontWeight = FontWeight.Bold) }
                Column(Modifier.weight(1f)) {
                    Text("Red de apoyo", style = MaterialTheme.typography.titleSmall.copy(fontFamily = OutfitFamily, fontWeight = FontWeight.Bold, color = T1, fontSize = 14.sp))
                    Text("María González · Hija de Carlos", style = MaterialTheme.typography.labelSmall.copy(color = T3, fontSize = 10.5.sp))
                }
                Box(Modifier.clip(RoundedCornerShape(20.dp)).background(Amber.copy(alpha = 0.9f)).padding(7.dp, 2.dp)) { Text("1", style = MaterialTheme.typography.labelSmall.copy(color = Color.White, fontWeight = FontWeight.ExtraBold, fontSize = 9.5.sp)) }
                Box(Modifier.clip(RoundedCornerShape(8.dp)).border(1.dp, B2, RoundedCornerShape(8.dp)).clickable { onChangeRole() }.padding(11.dp, 5.dp)) { Text("Rol", style = MaterialTheme.typography.labelSmall.copy(color = T2, fontSize = 11.sp)) }
                Box(Modifier.size(28.dp).clip(CircleShape).background(Brush.linearGradient(listOf(Color(0xFFF59E0B), Color(0xFFD97706)))), contentAlignment = Alignment.Center) { Text("MG", style = MaterialTheme.typography.labelSmall.copy(color = Color.White, fontWeight = FontWeight.Bold, fontSize = 10.5.sp)) }
            }
        },
        bottomBar = {
            Row(Modifier.fillMaxWidth().background(Bg1).border(1.dp, B1, RoundedCornerShape(0.dp)).padding(10.dp, 8.dp), horizontalArrangement = Arrangement.spacedBy(5.dp)) {
                FamilyTab.values().forEach { tab ->
                    val isActive = selectedTab == tab
                    val tabColor by animateColorAsState(if (isActive) Teal else T4, tween(160), label = "tc")
                    val bgColor by animateColorAsState(if (isActive) TealDim else Color.Transparent, tween(160), label = "bg")
                    Column(Modifier.weight(1f).clip(RoundedCornerShape(12.dp)).background(bgColor).clickable { selectedTab = tab; if (tab != FamilyTab.Overview) scope.launch { snackbar.showSnackbar(when (tab) { FamilyTab.Meds -> "💊 Metformina · Tomada hoy a las 8:30 am"; FamilyTab.Reports -> "📋 Último reporte enviado hace 2h · Dra. Peña"; else -> "" }) } }.padding(vertical = 8.dp, horizontal = 6.dp), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(4.dp)) {
                        Text(when (tab) { FamilyTab.Overview -> "🏠"; FamilyTab.Meds -> "💊"; FamilyTab.Reports -> "📋" }, fontSize = 20.sp)
                        Text(when (tab) { FamilyTab.Overview -> "Inicio"; FamilyTab.Meds -> "Medicamentos"; FamilyTab.Reports -> "Reportes" }, style = MaterialTheme.typography.labelSmall.copy(color = tabColor, fontFamily = DmSansFamily, fontWeight = FontWeight.SemiBold, fontSize = 10.5.sp))
                    }
                }
            }
        },
        snackbarHost = { SnackbarHost(snackbar) }
    ) { padding ->
        FamilyOverviewScreen(Modifier.padding(padding), snackbar)
    }
}

@Composable
fun FamilyOverviewScreen(modifier: Modifier = Modifier, snackbar: SnackbarHostState) {
    val scope = rememberCoroutineScope()
    LazyColumn(modifier = modifier.fillMaxSize(), contentPadding = PaddingValues(20.dp), verticalArrangement = Arrangement.spacedBy(14.dp)) {
        item {
            Text("¿Cómo está Carlos hoy?", style = MaterialTheme.typography.headlineLarge.copy(fontFamily = OutfitFamily, fontWeight = FontWeight.ExtraBold, color = T1))
            Spacer(Modifier.height(2.dp))
            Text("Sábado 7 de marzo · Actualizado hace 4 min", style = MaterialTheme.typography.bodySmall.copy(color = T3, fontFamily = DmSansFamily))
        }
        item {
            Row(Modifier.fillMaxWidth().clip(RoundedCornerShape(LargeRadius.dp)).background(Brush.linearGradient(listOf(Teal.copy(alpha = 0.12f), BlueAccent.copy(alpha = 0.07f)))).border(1.dp, B2, RoundedCornerShape(LargeRadius.dp)).padding(18.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(14.dp)) {
                Text("🛡️", fontSize = 30.sp)
                Column {
                    Text("Está siendo monitoreado ahora mismo", style = MaterialTheme.typography.titleMedium.copy(fontFamily = OutfitFamily, fontWeight = FontWeight.ExtraBold, color = T1))
                    Spacer(Modifier.height(4.dp))
                    Text("No tienes que llamar cada hora para saber si está bien. Continuum te avisa si algo cambia.", style = MaterialTheme.typography.bodySmall.copy(color = T2, fontFamily = DmSansFamily, lineHeight = 18.sp))
                }
            }
        }
        item {
            Row(Modifier.fillMaxWidth().clip(RoundedCornerShape(MedRadius.dp)).background(Amber.copy(alpha = 0.08f)).border(1.dp, Amber.copy(alpha = 0.25f), RoundedCornerShape(MedRadius.dp)).clickable { scope.launch { snackbar.showSnackbar("⚠️ La Dra. Peña ya fue notificada") } }.padding(14.dp, 12.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                Box(Modifier.size(32.dp).clip(CircleShape).background(Amber.copy(alpha = 0.2f)), contentAlignment = Alignment.Center) { Text("⚠️", fontSize = 16.sp) }
                Column(Modifier.weight(1f)) {
                    Text("Glucosa un poco alta hoy", style = MaterialTheme.typography.labelLarge.copy(color = T1, fontWeight = FontWeight.SemiBold, fontFamily = DmSansFamily, fontSize = 13.sp))
                    Text("La Dra. Peña ya fue notificada. No es urgente.", style = MaterialTheme.typography.labelSmall.copy(color = T2, fontSize = 11.sp))
                }
                Text("Ver →", style = MaterialTheme.typography.labelSmall.copy(color = Amber, fontWeight = FontWeight.SemiBold, fontSize = 11.sp))
            }
        }
        item {
            Row(horizontalArrangement = Arrangement.spacedBy(9.dp)) {
                listOf(Triple("🩸", "198", Pair("Glucosa", Pair("Un poco alta", Amber))), Triple("❤️", "74", Pair("Pulso", Pair("Normal ✓", Green))), Triple("💊", "✅", Pair("Medicamento", Pair("Tomado hoy", Green)))).forEach { (emoji, val_, ls) ->
                    Column(Modifier.weight(1f).clip(RoundedCornerShape(MedRadius.dp)).background(Bg2).border(1.dp, B1, RoundedCornerShape(MedRadius.dp)).clickable {}.padding(12.dp), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(2.dp)) {
                        Text(emoji, fontSize = 20.sp)
                        Text(val_, style = MaterialTheme.typography.titleSmall.copy(fontFamily = OutfitFamily, fontWeight = FontWeight.ExtraBold, color = T1, fontSize = 19.sp))
                        Text(ls.first, style = MaterialTheme.typography.labelSmall.copy(color = T2, fontSize = 10.5.sp))
                        Text(ls.second.first, style = MaterialTheme.typography.labelSmall.copy(color = ls.second.second, fontWeight = FontWeight.Bold, fontSize = 10.sp))
                    }
                }
            }
        }
        item {
            Column(Modifier.fillMaxWidth().clip(RoundedCornerShape(MedRadius.dp)).background(Bg2).border(1.dp, B1, RoundedCornerShape(MedRadius.dp)).padding(14.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text("🎙️ Carlos le dijo a Nova hoy a las 9:15 am", style = MaterialTheme.typography.labelSmall.copy(color = T3, fontFamily = DmSansFamily, fontSize = 11.sp))
                Text("\"Me siento un poco cansado y tuve dolor de cabeza después del almuerzo.\"", style = MaterialTheme.typography.bodyMedium.copy(color = T2, fontFamily = DmSansFamily, fontStyle = FontStyle.Italic, lineHeight = 21.sp))
                Text("→ Nova: posible fatiga posprandial · monitoreado", style = MaterialTheme.typography.labelSmall.copy(color = Amber, fontWeight = FontWeight.SemiBold, fontSize = 11.sp))
                Text("La Dra. Peña tiene esta información. Cita el martes 12 de marzo.", style = MaterialTheme.typography.labelSmall.copy(color = T3, fontSize = 11.sp))
            }
        }
        item {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text("Actividad reciente", style = MaterialTheme.typography.labelLarge.copy(color = T2, fontWeight = FontWeight.SemiBold, fontSize = 13.sp, fontFamily = DmSansFamily))
                listOf(Triple("💊", Triple("Medicamento tomado", "Carlos tomó la metformina.", "hace 1h"), true), Triple("🎙️", Triple("Registro de voz", "Carlos habló con Nova: cansancio.", "hace 2h"), true), Triple("📋", Triple("Reporte enviado", "Enviado a Dra. Peña.", "hace 2h"), false), Triple("❤️", Triple("Signos vitales", "Pulso 74 bpm, SpO2 97%.", "hace 4 min"), false)).forEach { (emoji, info, isNew) ->
                    Row(Modifier.fillMaxWidth().clip(RoundedCornerShape(MedRadius.dp)).background(Bg2).then(if (isNew) Modifier.border(1.dp, B2, RoundedCornerShape(MedRadius.dp)) else Modifier.border(1.dp, B1, RoundedCornerShape(MedRadius.dp))).padding(12.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(11.dp)) {
                        Box(Modifier.size(32.dp).clip(CircleShape).background(T4.copy(alpha = 0.5f)), contentAlignment = Alignment.Center) { Text(emoji, fontSize = 14.sp) }
                        Column(Modifier.weight(1f)) {
                            Text(info.first, style = MaterialTheme.typography.labelLarge.copy(color = T1, fontWeight = FontWeight.SemiBold, fontSize = 12.5.sp))
                            Text(info.second, style = MaterialTheme.typography.labelSmall.copy(color = T2, fontSize = 11.sp))
                            Text(info.third, style = MaterialTheme.typography.labelSmall.copy(color = T3, fontSize = 10.sp))
                        }
                        if (isNew) Text("●", style = MaterialTheme.typography.labelSmall.copy(color = Teal, fontSize = 12.sp))
                    }
                }
            }
        }
    }
}
