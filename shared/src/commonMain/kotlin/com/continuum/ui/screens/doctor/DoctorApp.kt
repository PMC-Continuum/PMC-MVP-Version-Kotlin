package com.continuum.ui.screens.doctor

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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import com.continuum.ui.theme.Bg3
import com.continuum.ui.theme.BlueAccent
import com.continuum.ui.theme.DmSansFamily
import com.continuum.ui.theme.Green
import com.continuum.ui.theme.MedRadius
import com.continuum.ui.theme.OutfitFamily
import com.continuum.ui.theme.PurpleRole
import com.continuum.ui.theme.Red
import com.continuum.ui.theme.T1
import com.continuum.ui.theme.T2
import com.continuum.ui.theme.T3
import com.continuum.ui.theme.T4
import com.continuum.ui.theme.Teal
import com.continuum.ui.theme.TealDim
import kotlinx.coroutines.launch

enum class DoctorTab { Patients, Alerts, Agenda, Trends }

@Composable
fun DoctorApp(onChangeRole: () -> Unit) {
    var selectedTab by remember { mutableStateOf(DoctorTab.Patients) }
    var showDetail by remember { mutableStateOf(false) }
    val snackbar = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    if (showDetail) { DoctorPatientDetail(onBack = { showDetail = false }); return }

    Scaffold(
        containerColor = Bg0,
        topBar = {
            Row(Modifier.fillMaxWidth().background(Bg1).border(1.dp, B1, RoundedCornerShape(0.dp)).padding(horizontal = 18.dp).height(54.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(11.dp)) {
                Box(Modifier.size(32.dp).clip(RoundedCornerShape(10.dp)).background(Brush.linearGradient(listOf(Teal, BlueAccent))), contentAlignment = Alignment.Center) { Text("∞", color = Color.White, fontSize = 14.sp, fontWeight = FontWeight.Bold) }
                Column(Modifier.weight(1f)) {
                    Text("Mis pacientes", style = MaterialTheme.typography.titleSmall.copy(fontFamily = OutfitFamily, fontWeight = FontWeight.Bold, color = T1, fontSize = 14.sp))
                    Text("Dra. Adriana Peña · Endocrinología", style = MaterialTheme.typography.labelSmall.copy(color = T3, fontSize = 10.5.sp))
                }
                Box(Modifier.clip(RoundedCornerShape(20.dp)).background(PurpleRole.copy(alpha = 0.9f)).padding(7.dp, 2.dp)) { Text("2 urgentes", style = MaterialTheme.typography.labelSmall.copy(color = Color.White, fontWeight = FontWeight.Bold, fontSize = 9.5.sp)) }
                Box(Modifier.clip(RoundedCornerShape(8.dp)).border(1.dp, B2, RoundedCornerShape(8.dp)).clickable { onChangeRole() }.padding(11.dp, 5.dp)) { Text("Rol", style = MaterialTheme.typography.labelSmall.copy(color = T2, fontSize = 11.sp)) }
                Box(Modifier.size(28.dp).clip(CircleShape).background(Brush.linearGradient(listOf(PurpleRole, Color(0xFF7C3AED)))), contentAlignment = Alignment.Center) { Text("AP", style = MaterialTheme.typography.labelSmall.copy(color = Color.White, fontWeight = FontWeight.Bold, fontSize = 10.5.sp)) }
            }
        },
        bottomBar = {
            Row(Modifier.fillMaxWidth().background(Bg1).border(1.dp, B1, RoundedCornerShape(0.dp)).padding(10.dp, 8.dp), horizontalArrangement = Arrangement.spacedBy(5.dp)) {
                DoctorTab.values().forEach { tab ->
                    val isActive = selectedTab == tab
                    val tabColor by animateColorAsState(if (isActive) Teal else T4, tween(160), label = "tc")
                    val bgColor by animateColorAsState(if (isActive) TealDim else Color.Transparent, tween(160), label = "bg")
                    Column(Modifier.weight(1f).clip(RoundedCornerShape(12.dp)).background(bgColor).clickable { selectedTab = tab; if (tab != DoctorTab.Patients) scope.launch { snackbar.showSnackbar(when (tab) { DoctorTab.Alerts -> "⚠️ 2 alertas activas: Lucía y Carlos"; DoctorTab.Agenda -> "📅 Hoy: 3 citas · próxima 10:30 am"; DoctorTab.Trends -> "📊 Glucosa panel: 168 mg/dL ↑"; else -> "" }) } }.padding(vertical = 8.dp, horizontal = 6.dp), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(4.dp)) {
                        Text(when (tab) { DoctorTab.Patients -> "👥"; DoctorTab.Alerts -> "🔔"; DoctorTab.Agenda -> "📅"; DoctorTab.Trends -> "📈" }, fontSize = 20.sp)
                        Text(when (tab) { DoctorTab.Patients -> "Pacientes"; DoctorTab.Alerts -> "Alertas"; DoctorTab.Agenda -> "Agenda"; DoctorTab.Trends -> "Tendencias" }, style = MaterialTheme.typography.labelSmall.copy(color = tabColor, fontFamily = DmSansFamily, fontWeight = FontWeight.SemiBold, fontSize = 10.5.sp))
                    }
                }
            }
        },
        snackbarHost = { SnackbarHost(snackbar) }
    ) { padding ->
        DoctorPatientList(modifier = Modifier.padding(padding), onPatientClick = { showDetail = true })
    }
}

@Composable
private fun DoctorPatientList(modifier: Modifier = Modifier, onPatientClick: () -> Unit) {
    data class Pat(val initials: String, val name: String, val condition: String, val alert: String, val alertColor: Color, val urgency: String, val date: String, val pre: Boolean, val tappable: Boolean = false)
    val patients = listOf(
        Pat("CM", "Carlos Méndez", "DM2 + HTA", "Glucosa alta · 198 mg/dL", Red, "urgent", "Mar 12", true, true),
        Pat("LR", "Lucía Rodríguez", "DM2 + Cardiopatía", "Glucosa crítica · 267 mg/dL", Red, "urgent", "Mar 11", false),
        Pat("JA", "Jorge Ávila", "HTA + IRC", "Presión 145/92 mmHg", Amber, "warn", "Mar 14", true),
        Pat("RE", "Rebeca Estrada", "DM2", "Adherencia 58%", Amber, "warn", "Mar 15", false),
        Pat("SM", "Santiago Mora", "HTA", "Control estable · 7 días", Green, "ok", "Mar 20", true)
    )
    LazyColumn(modifier = modifier.fillMaxSize(), contentPadding = PaddingValues(20.dp), verticalArrangement = Arrangement.spacedBy(9.dp)) {
        item {
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text("Mis pacientes crónicos", style = MaterialTheme.typography.headlineLarge.copy(fontFamily = OutfitFamily, fontWeight = FontWeight.ExtraBold, color = T1))
                Text("Ordenados por urgencia clínica · Clinical Sieve AI", style = MaterialTheme.typography.bodySmall.copy(color = T3, fontFamily = DmSansFamily))
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.padding(top = 8.dp)) {
                    listOf(Pair("● 2 urgentes", Red), Pair("● 2 atención", Amber), Pair("● 1 estable", Green)).forEach { (t, c) ->
                        Box(Modifier.clip(RoundedCornerShape(20.dp)).background(c.copy(alpha = 0.15f)).padding(horizontal = 10.dp, vertical = 3.dp)) { Text(t, style = MaterialTheme.typography.labelSmall.copy(color = c, fontWeight = FontWeight.Bold, fontSize = 10.5.sp, fontFamily = DmSansFamily)) }
                    }
                }
            }
        }
        items(patients) { p ->
            val borderColor = when (p.urgency) { "urgent" -> Red.copy(alpha = 0.30f); "warn" -> Amber.copy(alpha = 0.25f); else -> B1 }
            val bgTint = when (p.urgency) { "urgent" -> Red.copy(alpha = 0.04f); "warn" -> Amber.copy(alpha = 0.04f); else -> Color.Transparent }
            Row(Modifier.fillMaxWidth().clip(RoundedCornerShape(MedRadius.dp)).background(Bg2).background(bgTint).border(1.dp, borderColor, RoundedCornerShape(MedRadius.dp)).clickable { if (p.tappable) onPatientClick() }.padding(14.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(13.dp)) {
                Box(Modifier.size(40.dp).clip(CircleShape).background(p.alertColor.copy(alpha = 0.2f)), contentAlignment = Alignment.Center) { Text(p.initials, style = MaterialTheme.typography.labelLarge.copy(color = p.alertColor, fontWeight = FontWeight.Bold, fontSize = 13.sp)) }
                Column(Modifier.weight(1f)) {
                    Text(p.name, style = MaterialTheme.typography.titleSmall.copy(fontFamily = OutfitFamily, fontWeight = FontWeight.Bold, color = T1, fontSize = 13.5.sp))
                    Text(p.condition, style = MaterialTheme.typography.labelSmall.copy(color = T2, fontSize = 11.sp))
                    Text(p.alert, style = MaterialTheme.typography.labelSmall.copy(color = p.alertColor, fontWeight = FontWeight.SemiBold, fontSize = 10.5.sp))
                }
                Column(horizontalAlignment = Alignment.End, verticalArrangement = Arrangement.spacedBy(3.dp)) {
                    Box(Modifier.size(8.dp).clip(CircleShape).background(p.alertColor))
                    Text(p.date, style = MaterialTheme.typography.labelSmall.copy(color = T3, fontSize = 10.sp))
                    if (p.pre) Text("Pre-consulta ✓", style = MaterialTheme.typography.labelSmall.copy(color = Teal, fontWeight = FontWeight.SemiBold, fontSize = 10.sp))
                }
            }
        }
    }
}

@Composable
private fun DoctorPatientDetail(onBack: () -> Unit) {
    LazyColumn(Modifier.fillMaxSize(), contentPadding = PaddingValues(20.dp), verticalArrangement = Arrangement.spacedBy(14.dp)) {
        item {
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
                Box(Modifier.clip(RoundedCornerShape(8.dp)).border(1.dp, B1, RoundedCornerShape(8.dp)).clickable { onBack() }.padding(10.dp, 6.dp)) { Text("← Volver", style = MaterialTheme.typography.labelSmall.copy(color = T2, fontSize = 11.sp)) }
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("Carlos Méndez", style = MaterialTheme.typography.titleSmall.copy(fontFamily = OutfitFamily, fontWeight = FontWeight.Bold, color = T1, fontSize = 14.sp))
                    Text("DM2 + HTA", style = MaterialTheme.typography.labelSmall.copy(color = T3, fontSize = 10.5.sp))
                }
                Box(Modifier.clip(RoundedCornerShape(8.dp)).background(Brush.linearGradient(listOf(Teal, BlueAccent))).clickable {}.padding(10.dp, 6.dp)) { Text("✉️ Contactar", style = MaterialTheme.typography.labelSmall.copy(color = Color(0xFF060D18), fontWeight = FontWeight.Bold, fontSize = 11.sp)) }
            }
        }
        item {
            Column(Modifier.fillMaxWidth().clip(RoundedCornerShape(MedRadius.dp)).background(Brush.linearGradient(listOf(PurpleRole.copy(alpha = 0.14f), Teal.copy(alpha = 0.07f)))).border(1.dp, PurpleRole.copy(alpha = 0.30f), RoundedCornerShape(MedRadius.dp)).padding(16.dp), verticalArrangement = Arrangement.spacedBy(13.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    Box(Modifier.size(36.dp).clip(CircleShape).background(Brush.linearGradient(listOf(PurpleRole, Color(0xFF7C3AED)))), contentAlignment = Alignment.Center) { Text("🧠", fontSize = 16.sp) }
                    Column(Modifier.weight(1f)) {
                        Text("Resumen pre-consulta · Clinical Sieve AI", style = MaterialTheme.typography.titleSmall.copy(fontFamily = OutfitFamily, fontWeight = FontWeight.Bold, color = T1, fontSize = 13.sp))
                        Text("Generado 2h antes de la cita · 7 días de datos", style = MaterialTheme.typography.labelSmall.copy(color = T3, fontSize = 10.5.sp))
                    }
                    Box(Modifier.clip(RoundedCornerShape(20.dp)).background(PurpleRole.copy(alpha = 0.2f)).border(1.dp, PurpleRole.copy(alpha = 0.35f), RoundedCornerShape(20.dp)).padding(9.dp, 3.dp)) { Text("Listo", style = MaterialTheme.typography.labelSmall.copy(color = Color(0xFFC084FC), fontWeight = FontWeight.Bold, fontSize = 9.5.sp)) }
                }
                Column(verticalArrangement = Arrangement.spacedBy(9.dp)) {
                    listOf(Triple(Red, "Glucosa media 178 mg/dL. Adherencia baja (71%). Riesgo de descompensación.", "Revisar dosis de metformina"), Triple(Amber, "Presión promedio 127/81 mmHg. Dentro del rango objetivo.", "Mantener tratamiento actual"), Triple(Green, "FC estable 72 bpm. Sin arritmias detectadas.", "Sin cambios cardíacos requeridos")).forEach { (color, text, action) ->
                        Row(verticalAlignment = Alignment.Top, horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Box(Modifier.size(8.dp).clip(CircleShape).background(color))
                                Box(Modifier.width(1.dp).height(30.dp).background(B1))
                            }
                            Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                                Text(text, style = MaterialTheme.typography.bodySmall.copy(color = T1, fontSize = 12.5.sp, fontFamily = DmSansFamily, lineHeight = 18.sp))
                                Text(action, style = MaterialTheme.typography.labelSmall.copy(color = T3, fontStyle = FontStyle.Italic, fontSize = 10.5.sp))
                            }
                        }
                    }
                }
            }
        }
        item {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text("Registros de voz · última semana", style = MaterialTheme.typography.labelLarge.copy(color = T2, fontWeight = FontWeight.SemiBold, fontSize = 13.sp, fontFamily = DmSansFamily))
                listOf(Triple("Hoy 9:15 am", "\"Me siento cansado y tuve dolor de cabeza después del almuerzo.\"", Pair("→ Nova: posible fatiga posprandial", Amber)), Triple("Ayer 8:42 am", "\"Tomé la metformina, me siento bien hoy.\"", Pair("→ Nova: adherencia +1 · estado estable", Green)), Triple("Hace 3 días", "Sin registro de voz", Pair("→ Silencio clínico", T3))).forEach { (ts, text, analysis) ->
                    Column(Modifier.fillMaxWidth().clip(RoundedCornerShape(9.dp)).background(Bg3).padding(10.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                        Text(ts, style = MaterialTheme.typography.labelSmall.copy(color = T3, fontSize = 10.sp))
                        Text(text, style = MaterialTheme.typography.bodySmall.copy(color = T2, fontStyle = FontStyle.Italic, fontSize = 12.sp))
                        Text(analysis.first, style = MaterialTheme.typography.labelSmall.copy(color = analysis.second, fontWeight = FontWeight.SemiBold, fontSize = 10.5.sp))
                    }
                }
            }
        }
    }
}
