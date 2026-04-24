package com.continuum.ui.screens.patient

import androidx.compose.animation.core.EaseInOutSine
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.continuum.ui.theme.Amber
import com.continuum.ui.theme.B1
import com.continuum.ui.theme.Bg2
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
fun PatientSOSScreen(modifier: Modifier = Modifier, snackbar: SnackbarHostState) {
    var showConfirm by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    val tr = rememberInfiniteTransition(label = "sos")
    val p1 by tr.animateFloat(18f, 26f, infiniteRepeatable(tween(3000, easing = EaseInOutSine), RepeatMode.Reverse), label = "p1")
    val p2 by tr.animateFloat(36f, 52f, infiniteRepeatable(tween(3000, easing = EaseInOutSine), RepeatMode.Reverse), label = "p2")

    Column(modifier = modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(horizontal = 20.dp, vertical = 28.dp), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(22.dp)) {
        Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(6.dp), modifier = Modifier.widthIn(max = 310.dp)) {
            Text("Emergencia SOS", style = MaterialTheme.typography.headlineMedium.copy(fontFamily = OutfitFamily, fontWeight = FontWeight.ExtraBold, color = T1))
            Text("Al presionar, Continuum notifica a tu red de apoyo, envía tu historial clínico y activa la ruta al hospital con menor espera.", style = MaterialTheme.typography.bodyMedium.copy(color = T2, fontFamily = DmSansFamily, textAlign = TextAlign.Center), textAlign = TextAlign.Center)
        }
        Box(
            modifier = Modifier.size(136.dp)
                .drawBehind {
                    drawCircle(Red.copy(alpha = 0.15f), radius = size.minDimension / 2f + p1)
                    drawCircle(Red.copy(alpha = 0.06f), radius = size.minDimension / 2f + p2)
                }
                .clip(CircleShape).background(Brush.linearGradient(listOf(Red, Color(0xFF922B21)))).clickable { showConfirm = true },
            contentAlignment = Alignment.Center
        ) { Text("SOS", style = MaterialTheme.typography.displaySmall.copy(fontFamily = OutfitFamily, fontWeight = FontWeight.Black, color = Color.White, fontSize = 19.sp, letterSpacing = 0.05.sp)) }
        Text("⚙️ Algoritmo de grafos · tiempos de espera en tiempo real", style = MaterialTheme.typography.labelSmall.copy(color = T3, fontFamily = DmSansFamily, fontSize = 11.sp), textAlign = TextAlign.Center)
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            listOf(Triple("Hospital Santa Fe", "1.2 km · ~4 min", Pair("● Poca espera", Green)), Triple("Clínica Colsanitas", "2.8 km · ~9 min", Pair("● Espera media", Amber)), Triple("Clínica del Country", "4.1 km · ~12 min", Pair("● Poca espera", Green))).forEach { (name, time, occ) ->
                Column(
                    modifier = Modifier.weight(1f).clip(RoundedCornerShape(MedRadius.dp)).background(Bg2).border(1.dp, B1, RoundedCornerShape(MedRadius.dp)).clickable { scope.launch { snackbar.showSnackbar("🚑 Ruta activada → $name") } }.padding(10.dp),
                    horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(2.dp)
                ) {
                    Text(name.split(" ").take(2).joinToString(" "), style = MaterialTheme.typography.labelSmall.copy(color = T1, fontWeight = FontWeight.Bold, fontSize = 11.5.sp), textAlign = TextAlign.Center)
                    Text(time, style = MaterialTheme.typography.labelSmall.copy(color = T3, fontSize = 11.sp))
                    Text(occ.first, style = MaterialTheme.typography.labelSmall.copy(color = occ.second, fontWeight = FontWeight.Bold, fontSize = 10.sp))
                }
            }
        }
        Column(Modifier.fillMaxWidth().clip(RoundedCornerShape(MedRadius.dp)).background(Bg2).border(1.dp, B1, RoundedCornerShape(MedRadius.dp)).padding(14.dp), verticalArrangement = Arrangement.spacedBy(7.dp)) {
            Text("Tu red de apoyo será notificada automáticamente", style = MaterialTheme.typography.labelLarge.copy(color = T2, fontWeight = FontWeight.SemiBold, fontSize = 11.5.sp, fontFamily = DmSansFamily))
            listOf(Triple("MG", "María González", Pair("Hija", Color(0xFFF59E0B))), Triple("AP", "Dra. Adriana Peña", Pair("Médica", Color(0xFFA855F7))), Triple("ES", "EPS Sura", Pair("Seguro", Teal))).forEach { (initials, name, role) ->
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(9.dp)) {
                    Box(Modifier.size(26.dp).clip(CircleShape).background(role.second.copy(alpha = 0.25f)), contentAlignment = Alignment.Center) {
                        Text(initials, fontSize = 10.sp, fontWeight = FontWeight.Bold, color = role.second)
                    }
                    Text(name, style = MaterialTheme.typography.bodySmall.copy(color = T1, fontSize = 12.sp, fontFamily = DmSansFamily))
                    Text(role.first, style = MaterialTheme.typography.labelSmall.copy(color = T3, fontSize = 11.sp))
                }
            }
        }
    }

    if (showConfirm) {
        AlertDialog(
            onDismissRequest = { showConfirm = false },
            title = { Text("¿Confirmar SOS?", style = MaterialTheme.typography.headlineSmall.copy(fontFamily = OutfitFamily, color = T1)) },
            text = { Text("Se notificará a tu red de apoyo y se enviará tu historial clínico al equipo de respuesta.", style = MaterialTheme.typography.bodyMedium.copy(color = T2, fontFamily = DmSansFamily)) },
            confirmButton = { TextButton(onClick = { showConfirm = false; scope.launch { snackbar.showSnackbar("🚨 SOS activado"); delay(2200); snackbar.showSnackbar("🚑 Ambulancia en camino · 4 min") } }) { Text("Confirmar SOS", color = Red, fontWeight = FontWeight.Bold) } },
            dismissButton = { TextButton(onClick = { showConfirm = false }) { Text("Cancelar", color = T2) } },
            containerColor = Bg2
        )
    }
}
