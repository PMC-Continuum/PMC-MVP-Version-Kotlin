package com.continuum.ui.screens.patient

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.continuum.ui.theme.Amber
import com.continuum.ui.theme.B1
import com.continuum.ui.theme.B2
import com.continuum.ui.theme.Bg2
import com.continuum.ui.theme.Bg3
import com.continuum.ui.theme.BlueAccent
import com.continuum.ui.theme.DmSansFamily
import com.continuum.ui.theme.Green
import com.continuum.ui.theme.MedRadius
import com.continuum.ui.theme.OutfitFamily
import com.continuum.ui.theme.Red
import com.continuum.ui.theme.T1
import com.continuum.ui.theme.T2
import com.continuum.ui.theme.T3
import com.continuum.ui.theme.Teal
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun PatientReportesScreen(modifier: Modifier = Modifier, snackbar: SnackbarHostState) {
    var generating by remember { mutableStateOf(false) }
    var genProgress by remember { mutableStateOf(0f) }
    val scope = rememberCoroutineScope()
    val animatedProgress by animateFloatAsState(genProgress, tween(400), label = "p")

    Column(modifier = modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(20.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
        Text("Reportes clínicos", style = MaterialTheme.typography.headlineLarge.copy(fontFamily = OutfitFamily, fontWeight = FontWeight.ExtraBold, color = T1))
        Text("Clinical Sieve AI · para Dra. Adriana Peña", style = MaterialTheme.typography.bodySmall.copy(color = T3, fontFamily = DmSansFamily))
        Spacer(Modifier.height(4.dp))
        ReportCard("📋", Teal.copy(alpha = 0.25f), BlueAccent.copy(alpha = 0.18f), "Resumen Semanal · Mar 1–7", "hace 2h", true,
            listOf(Triple("Glucosa prom.", "162 mg/dL", Pair("⚠ Elevado", Amber)), Triple("Presión prom.", "127/81", Pair("✓ OK", Green)), Triple("Adherencia", "71%", Pair("✗ Bajo", Red)), Triple("Registros voz", "5/7", Pair("⚠ Incompleto", Amber))))
        ReportCard("🫀", Green.copy(alpha = 0.20f), Teal.copy(alpha = 0.12f), "Tendencia Cardiovascular · Feb 22–28", "hace 9 días", false,
            listOf(Triple("FC", "72 bpm ✓", Pair("Normal", Green)), Triple("SpO2", "97% ✓", Pair("Normal", Green)), Triple("Sin arritmias", "✓", Pair("Normal", Green)), Triple("Presión", "126/80", Pair("Estable", Green))))
        if (generating) {
            Column(Modifier.fillMaxWidth().clip(RoundedCornerShape(MedRadius.dp)).background(Bg2).border(1.dp, B1, RoundedCornerShape(MedRadius.dp)).padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text("Generando reporte…", style = MaterialTheme.typography.labelLarge.copy(color = T2, fontFamily = DmSansFamily, fontSize = 11.sp))
                LinearProgressIndicator(progress = { animatedProgress }, modifier = Modifier.fillMaxWidth().height(3.dp).clip(RoundedCornerShape(2.dp)), color = Teal, trackColor = Bg3, strokeCap = StrokeCap.Round)
            }
        }
        Box(Modifier.fillMaxWidth().clip(RoundedCornerShape(MedRadius.dp)).border(1.dp, B2, RoundedCornerShape(MedRadius.dp)).clickable {
            if (!generating) { generating = true
                scope.launch { listOf(0.15f, 0.3f, 0.5f, 0.7f, 0.85f, 1f).forEach { s -> delay(500); genProgress = s }
                    delay(400); generating = false; genProgress = 0f; snackbar.showSnackbar("📋 Reporte generado — listo para enviar") }
            }
        }.padding(vertical = 14.dp), contentAlignment = Alignment.Center) {
            Text("+ Generar nuevo reporte", style = MaterialTheme.typography.bodyMedium.copy(color = T2, fontFamily = DmSansFamily, fontSize = 12.sp))
        }
        Box(Modifier.fillMaxWidth().clip(RoundedCornerShape(MedRadius.dp)).background(Brush.linearGradient(listOf(Teal, BlueAccent))).clickable { scope.launch { snackbar.showSnackbar("✉️ Reporte enviado a Dra. Peña") } }.padding(vertical = 14.dp), contentAlignment = Alignment.Center) {
            Text("✉️ Enviar reporte a la Dra. Peña", style = MaterialTheme.typography.labelLarge.copy(color = Color(0xFF060D18), fontWeight = FontWeight.Bold, fontSize = 13.sp))
        }
    }
}

@Composable
private fun ReportCard(emoji: String, gs: Color, ge: Color, title: String, date: String, sent: Boolean, metrics: List<Triple<String, String, Pair<String, Color>>>) {
    Column(Modifier.fillMaxWidth().clip(RoundedCornerShape(MedRadius.dp)).background(Bg2).border(1.dp, B1, RoundedCornerShape(MedRadius.dp)).clickable {}.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(11.dp)) {
            Box(Modifier.size(38.dp).clip(RoundedCornerShape(11.dp)).background(Brush.linearGradient(listOf(gs, ge))), contentAlignment = Alignment.Center) { Text(emoji, fontSize = 18.sp) }
            Column(Modifier.weight(1f)) {
                Text(title, style = MaterialTheme.typography.titleSmall.copy(fontFamily = OutfitFamily, fontWeight = FontWeight.Bold, color = T1, fontSize = 13.sp))
                Row(horizontalArrangement = Arrangement.spacedBy(4.dp), verticalAlignment = Alignment.CenterVertically) {
                    Text(date, style = MaterialTheme.typography.labelSmall.copy(color = T3, fontSize = 10.5.sp))
                    if (sent) Text("✓ Enviado", style = MaterialTheme.typography.labelSmall.copy(color = Green, fontWeight = FontWeight.SemiBold, fontSize = 10.sp))
                }
            }
        }
        Row(horizontalArrangement = Arrangement.spacedBy(7.dp)) { metrics.take(2).forEach { m -> MetricCell(m, Modifier.weight(1f)) } }
        Row(horizontalArrangement = Arrangement.spacedBy(7.dp)) { metrics.drop(2).forEach { m -> MetricCell(m, Modifier.weight(1f)) } }
    }
}

@Composable
private fun MetricCell(metric: Triple<String, String, Pair<String, Color>>, modifier: Modifier = Modifier) {
    Column(modifier.clip(RoundedCornerShape(9.dp)).background(Bg3).padding(9.dp)) {
        Text(metric.first.uppercase(), style = MaterialTheme.typography.labelSmall.copy(color = T2, fontSize = 9.5.sp, letterSpacing = 0.7.sp, fontFamily = DmSansFamily))
        Text(metric.second, style = MaterialTheme.typography.titleSmall.copy(fontFamily = OutfitFamily, fontWeight = FontWeight.Bold, color = T1, fontSize = 17.sp))
        Text(metric.third.first, style = MaterialTheme.typography.labelSmall.copy(color = metric.third.second, fontWeight = FontWeight.Bold, fontSize = 9.5.sp))
    }
}
