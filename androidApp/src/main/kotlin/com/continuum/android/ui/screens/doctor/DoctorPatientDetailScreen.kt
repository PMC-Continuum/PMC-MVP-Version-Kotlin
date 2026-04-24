package com.continuum.android.ui.screens.doctor

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.continuum.android.ui.theme.Amber
import com.continuum.android.ui.theme.B1
import com.continuum.android.ui.theme.B2
import com.continuum.android.ui.theme.Bg2
import com.continuum.android.ui.theme.Bg3
import com.continuum.android.ui.theme.BlueAccent
import com.continuum.android.ui.theme.DmSansFamily
import com.continuum.android.ui.theme.Green
import com.continuum.android.ui.theme.MedRadius
import com.continuum.android.ui.theme.OutfitFamily
import com.continuum.android.ui.theme.PurpleRole
import com.continuum.android.ui.theme.Red
import com.continuum.android.ui.theme.T1
import com.continuum.android.ui.theme.T2
import com.continuum.android.ui.theme.T3
import com.continuum.android.ui.theme.Teal

@Composable
fun DoctorPatientDetailScreen(onBack: () -> Unit, snackbar: SnackbarHostState) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = androidx.compose.foundation.layout.PaddingValues(20.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item {
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
                Box(Modifier.clip(RoundedCornerShape(8.dp)).border(1.dp, B1, RoundedCornerShape(8.dp)).clickable { onBack() }.padding(10.dp, 6.dp)) {
                    Text("← Volver", style = MaterialTheme.typography.labelSmall.copy(color = T2, fontSize = 11.sp))
                }
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("Carlos Méndez", style = MaterialTheme.typography.titleSmall.copy(fontFamily = OutfitFamily, fontWeight = FontWeight.Bold, color = T1, fontSize = 14.sp))
                    Text("DM2 + HTA", style = MaterialTheme.typography.labelSmall.copy(color = T3, fontSize = 10.5.sp))
                }
                Box(Modifier.clip(RoundedCornerShape(8.dp)).background(Brush.linearGradient(listOf(Teal, BlueAccent))).clickable { }.padding(10.dp, 6.dp)) {
                    Text("✉️ Contactar", style = MaterialTheme.typography.labelSmall.copy(color = Color(0xFF060D18), fontWeight = FontWeight.Bold, fontSize = 11.sp))
                }
            }
        }
        item { PreConsultCard() }
        item {
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                listOf(Triple("198 mg/dL", "Glucosa", Amber), Triple("128/82", "Presión", Green)).forEach { (v, n, c) ->
                    Column(
                        modifier = Modifier.weight(1f).clip(RoundedCornerShape(MedRadius.dp)).background(Bg2).border(1.dp, B1, RoundedCornerShape(MedRadius.dp)).padding(14.dp),
                        verticalArrangement = Arrangement.spacedBy(2.dp)
                    ) {
                        Text(v, style = MaterialTheme.typography.titleSmall.copy(fontFamily = OutfitFamily, fontWeight = FontWeight.Bold, color = T1, fontSize = 18.sp))
                        Text(n, style = MaterialTheme.typography.labelSmall.copy(color = T2, fontSize = 11.sp))
                        Text("Hoy", style = MaterialTheme.typography.labelSmall.copy(color = c, fontWeight = FontWeight.Bold, fontSize = 11.sp))
                    }
                }
                listOf(Triple("74 bpm", "FC", Green), Triple("97%", "SpO2", Green)).forEach { (v, n, c) ->
                    Column(
                        modifier = Modifier.weight(1f).clip(RoundedCornerShape(MedRadius.dp)).background(Bg2).border(1.dp, B1, RoundedCornerShape(MedRadius.dp)).padding(14.dp),
                        verticalArrangement = Arrangement.spacedBy(2.dp)
                    ) {
                        Text(v, style = MaterialTheme.typography.titleSmall.copy(fontFamily = OutfitFamily, fontWeight = FontWeight.Bold, color = T1, fontSize = 18.sp))
                        Text(n, style = MaterialTheme.typography.labelSmall.copy(color = T2, fontSize = 11.sp))
                        Text("Normal", style = MaterialTheme.typography.labelSmall.copy(color = c, fontWeight = FontWeight.Bold, fontSize = 11.sp))
                    }
                }
            }
        }
        item {
            Column(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text("Registros de voz · última semana", style = MaterialTheme.typography.labelLarge.copy(color = T2, fontWeight = FontWeight.SemiBold, fontSize = 13.sp, fontFamily = DmSansFamily))
                listOf(
                    Triple("Hoy 9:15 am", "\"Me siento cansado y tuve dolor de cabeza después del almuerzo.\"", Pair("→ Nova: posible fatiga posprandial · glucosa alta", Amber)),
                    Triple("Ayer 8:42 am", "\"Tomé la metformina, me siento bien hoy.\"", Pair("→ Nova: adherencia +1 · estado estable", Green)),
                    Triple("Hace 3 días", "Sin registro de voz", Pair("→ Silencio clínico", T3))
                ).forEach { (ts, text, analysis) ->
                    Column(
                        modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(9.dp)).background(Bg3).padding(10.dp, 10.dp),
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Text(ts, style = MaterialTheme.typography.labelSmall.copy(color = T3, fontSize = 10.sp))
                        Text(text, style = MaterialTheme.typography.bodySmall.copy(color = T2, fontStyle = FontStyle.Italic, fontSize = 12.sp))
                        Text(analysis.first, style = MaterialTheme.typography.labelSmall.copy(color = analysis.second, fontWeight = FontWeight.SemiBold, fontSize = 10.5.sp))
                    }
                }
            }
        }
    }
}

@Composable
private fun PreConsultCard() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(MedRadius.dp))
            .background(Brush.linearGradient(listOf(PurpleRole.copy(alpha = 0.14f), Teal.copy(alpha = 0.07f))))
            .border(1.dp, PurpleRole.copy(alpha = 0.30f), RoundedCornerShape(MedRadius.dp))
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(13.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            Box(
                modifier = Modifier.size(36.dp).clip(CircleShape).background(Brush.linearGradient(listOf(PurpleRole, Color(0xFF7C3AED)))),
                contentAlignment = Alignment.Center
            ) { Text("🧠", fontSize = 16.sp) }
            Column(modifier = Modifier.weight(1f)) {
                Text("Resumen pre-consulta · Clinical Sieve AI", style = MaterialTheme.typography.titleSmall.copy(fontFamily = OutfitFamily, fontWeight = FontWeight.Bold, color = T1, fontSize = 13.sp))
                Text("Generado 2h antes de la cita · 7 días de datos", style = MaterialTheme.typography.labelSmall.copy(color = T3, fontSize = 10.5.sp))
            }
            Box(Modifier.clip(RoundedCornerShape(20.dp)).background(PurpleRole.copy(alpha = 0.2f)).border(1.dp, PurpleRole.copy(alpha = 0.35f), RoundedCornerShape(20.dp)).padding(9.dp, 3.dp)) {
                Text("Listo", style = MaterialTheme.typography.labelSmall.copy(color = Color(0xFFC084FC), fontWeight = FontWeight.Bold, fontSize = 9.5.sp))
            }
        }
        Column(verticalArrangement = Arrangement.spacedBy(9.dp)) {
            listOf(
                Triple(Red, "Glucosa media 178 mg/dL. Adherencia baja (71%). Riesgo de descompensación.", "Revisar dosis de metformina"),
                Triple(Amber, "Presión promedio 127/81 mmHg. Dentro del rango objetivo.", "Mantener tratamiento actual"),
                Triple(Green, "FC estable 72 bpm. Sin arritmias detectadas en registros de voz.", "Sin cambios cardíacos requeridos")
            ).forEach { (color, text, action) ->
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
