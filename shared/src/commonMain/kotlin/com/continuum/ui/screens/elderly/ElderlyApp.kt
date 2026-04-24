package com.continuum.ui.screens.elderly

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.EaseInOutSine
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.continuum.ui.animation.breatheAnimation
import com.continuum.ui.theme.Amber
import com.continuum.ui.theme.BlueAccent
import com.continuum.ui.theme.DmSansFamily
import com.continuum.ui.theme.Green
import com.continuum.ui.theme.OutfitFamily
import com.continuum.ui.theme.Red
import com.continuum.ui.theme.Teal
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

private val ElderlyBg = Brush.linearGradient(listOf(Color(0xFF08182E), Color(0xFF0A1E38), Color(0xFF061520)))

enum class ElderlyTab { Home, Voice, Vitals, SOS }

@Composable
fun ElderlyApp(onChangeRole: () -> Unit) {
    var selectedTab by remember { mutableStateOf(ElderlyTab.Home) }
    val snackbar = remember { SnackbarHostState() }

    Box(Modifier.fillMaxSize().background(ElderlyBg)) {
        Column(Modifier.fillMaxSize()) {
            Row(Modifier.fillMaxWidth().background(Color.White.copy(alpha = 0.05f)).border(1.dp, Color.White.copy(alpha = 0.08f), RoundedCornerShape(0.dp)).padding(horizontal = 20.dp, vertical = 14.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween) {
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(11.dp)) {
                    Box(Modifier.size(42.dp).clip(RoundedCornerShape(12.dp)).background(Brush.linearGradient(listOf(Teal, BlueAccent))), contentAlignment = Alignment.Center) { Text("∞", color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Bold) }
                    Text("Continuum", style = MaterialTheme.typography.displaySmall.copy(fontFamily = OutfitFamily, fontWeight = FontWeight.ExtraBold, color = Color.White, fontSize = 21.sp))
                }
                Box(Modifier.clip(RoundedCornerShape(9.dp)).background(Color.White.copy(alpha = 0.08f)).border(1.dp, Color.White.copy(alpha = 0.16f), RoundedCornerShape(9.dp)).clickable { onChangeRole() }.padding(horizontal = 16.dp, vertical = 8.dp)) {
                    Text("← Salir", style = MaterialTheme.typography.labelLarge.copy(color = Color.White, fontFamily = OutfitFamily, fontWeight = FontWeight.Bold, fontSize = 13.sp))
                }
            }
            Box(Modifier.weight(1f)) {
                AnimatedContent(selectedTab, transitionSpec = { fadeIn(tween(280)) togetherWith fadeOut(tween(180)) }, label = "tab") { tab ->
                    when (tab) {
                        ElderlyTab.Home   -> ElderlyHome(onNavigate = { selectedTab = it }, snackbar)
                        ElderlyTab.Voice  -> ElderlyVoice(snackbar)
                        ElderlyTab.Vitals -> ElderlyVitals()
                        ElderlyTab.SOS    -> ElderlySOS(snackbar)
                    }
                }
            }
            Row(Modifier.fillMaxWidth().background(Color.White.copy(alpha = 0.055f)).border(1.dp, Color.White.copy(alpha = 0.08f), RoundedCornerShape(0.dp)).padding(horizontal = 14.dp, vertical = 10.dp), horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                ElderlyTab.values().forEach { tab ->
                    val isActive = selectedTab == tab
                    Column(Modifier.weight(1f).clip(RoundedCornerShape(12.dp)).background(if (isActive) Teal.copy(alpha = 0.2f) else Color.Transparent).clickable { selectedTab = tab }.padding(vertical = 10.dp, horizontal = 6.dp), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(4.dp)) {
                        Text(when (tab) { ElderlyTab.Home -> "🏠"; ElderlyTab.Voice -> "🎙️"; ElderlyTab.Vitals -> "📊"; ElderlyTab.SOS -> "🚨" }, fontSize = 22.sp)
                        Text(when (tab) { ElderlyTab.Home -> "Inicio"; ElderlyTab.Voice -> "Hablar"; ElderlyTab.Vitals -> "Mis datos"; ElderlyTab.SOS -> "SOS" },
                            style = MaterialTheme.typography.labelSmall.copy(color = if (tab == ElderlyTab.SOS) Red else if (isActive) Color.White else Color.White.copy(alpha = 0.38f), fontFamily = OutfitFamily, fontWeight = FontWeight.Bold, fontSize = 11.sp))
                    }
                }
            }
        }
        SnackbarHost(snackbar, Modifier.align(Alignment.BottomCenter).padding(bottom = 80.dp))
    }
}

@Composable
private fun ElderlyHome(onNavigate: (ElderlyTab) -> Unit, snackbar: SnackbarHostState) {
    Column(Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(18.dp), verticalArrangement = Arrangement.spacedBy(24.dp)) {
        Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
            Text("¡Buenos días,\nCarlos! 👋", style = MaterialTheme.typography.displayLarge.copy(fontFamily = OutfitFamily, fontWeight = FontWeight.ExtraBold, color = Color.White, fontSize = 32.sp, textAlign = TextAlign.Center, lineHeight = 40.sp), textAlign = TextAlign.Center)
            Spacer(Modifier.height(6.dp))
            Text("Sábado 7 de marzo", style = MaterialTheme.typography.bodyLarge.copy(color = Color.White.copy(alpha = 0.5f), fontSize = 17.sp))
        }
        Row(Modifier.fillMaxWidth().clip(RoundedCornerShape(18.dp)).background(Brush.linearGradient(listOf(Teal.copy(alpha = 0.2f), BlueAccent.copy(alpha = 0.12f)))).border(1.5.dp, Teal.copy(alpha = 0.3f), RoundedCornerShape(18.dp)).padding(16.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(13.dp)) {
            Text("🛡️", fontSize = 30.sp)
            Column {
                Text("Tu familia y tu médica están al tanto", style = MaterialTheme.typography.titleMedium.copy(fontFamily = OutfitFamily, fontWeight = FontWeight.ExtraBold, color = Color.White, fontSize = 16.sp))
                Spacer(Modifier.height(3.dp))
                Text("María y la Dra. Peña reciben actualizaciones. No estás solo.", style = MaterialTheme.typography.bodySmall.copy(color = Color.White.copy(alpha = 0.55f), fontSize = 13.sp))
            }
        }
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            listOf(Triple("🩸", "198", Triple("Glucosa mg/dL", "⚠ Alta", Amber)), Triple("❤️", "74", Triple("Pulso bpm", "✓ Normal", Green)), Triple("🫁", "97%", Triple("Oxígeno SpO2", "✓ Bien", Green))).forEach { (emoji, value, info) ->
                Column(Modifier.weight(1f).clip(RoundedCornerShape(17.dp)).background(Color.White.copy(alpha = 0.06f)).border(1.5.dp, Color.White.copy(alpha = 0.09f), RoundedCornerShape(17.dp)).padding(12.dp), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Text(emoji, fontSize = 28.sp)
                    Text(value, style = MaterialTheme.typography.displaySmall.copy(fontFamily = OutfitFamily, fontWeight = FontWeight.Black, color = Color.White, fontSize = 28.sp))
                    Text(info.first, style = MaterialTheme.typography.labelSmall.copy(color = Color.White.copy(alpha = 0.5f), fontFamily = OutfitFamily, fontWeight = FontWeight.Bold, fontSize = 13.sp), textAlign = TextAlign.Center)
                    Text(info.second, style = MaterialTheme.typography.labelSmall.copy(color = info.third, fontWeight = FontWeight.ExtraBold, fontSize = 12.sp))
                }
            }
        }
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            listOf(Triple("🎙️", "¿Cómo\nme siento?", ElderlyTab.Voice), Triple("💊", "Tomé el\nmedicamento", ElderlyTab.Home)).forEach { (emoji, label, nav) ->
                Column(Modifier.weight(1f).clip(RoundedCornerShape(20.dp)).background(if (nav == ElderlyTab.Voice) Brush.linearGradient(listOf(BlueAccent.copy(alpha = 0.44f), Teal.copy(alpha = 0.24f))) else Brush.linearGradient(listOf(Green.copy(alpha = 0.22f), Color(0xFF10B981).copy(alpha = 0.13f)))).border(2.dp, Color.White.copy(alpha = 0.1f), RoundedCornerShape(20.dp)).clickable { onNavigate(nav) }.padding(vertical = 28.dp, horizontal = 16.dp), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Text(emoji, fontSize = 42.sp)
                    Text(label, style = MaterialTheme.typography.titleLarge.copy(fontFamily = OutfitFamily, fontWeight = FontWeight.ExtraBold, color = Color.White, fontSize = 18.sp, textAlign = TextAlign.Center, lineHeight = 24.sp), textAlign = TextAlign.Center)
                }
            }
        }
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            listOf(Triple("📧", "Avisar a\nmi médica", ElderlyTab.Home), Triple("🚨", "Pedir\nayuda", ElderlyTab.SOS)).forEach { (emoji, label, nav) ->
                Column(Modifier.weight(1f).clip(RoundedCornerShape(20.dp)).background(if (nav == ElderlyTab.SOS) Brush.linearGradient(listOf(Red.copy(alpha = 0.35f), Color(0xFFB91C1C).copy(alpha = 0.2f))) else Brush.linearGradient(listOf(Amber.copy(alpha = 0.22f), Color(0xFFEA580C).copy(alpha = 0.13f)))).border(2.dp, Color.White.copy(alpha = 0.1f), RoundedCornerShape(20.dp)).clickable { onNavigate(nav) }.padding(vertical = 28.dp, horizontal = 16.dp), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Text(emoji, fontSize = 42.sp)
                    Text(label, style = MaterialTheme.typography.titleLarge.copy(fontFamily = OutfitFamily, fontWeight = FontWeight.ExtraBold, color = Color.White, fontSize = 18.sp, textAlign = TextAlign.Center, lineHeight = 24.sp), textAlign = TextAlign.Center)
                }
            }
        }
    }
}

@Composable
private fun ElderlyVoice(snackbar: SnackbarHostState) {
    var recording by remember { mutableStateOf(false) }
    var transcription by remember { mutableStateOf("") }
    val breathe by breatheAnimation()
    val tr = rememberInfiniteTransition(label = "mic")
    val pulse by tr.animateFloat(0f, 1f, infiniteRepeatable(tween(1200), RepeatMode.Restart), label = "p")
    val scope = rememberCoroutineScope()
    val delays = listOf(0, 100, 200, 300, 400, 300, 200)
    val heights = listOf(0.25f, 0.55f, 0.90f, 0.68f, 0.46f, 0.76f, 0.54f)

    Column(Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(18.dp), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(20.dp)) {
        Spacer(Modifier.height(16.dp))
        Text("¿Cómo te sientes hoy?", style = MaterialTheme.typography.displayMedium.copy(fontFamily = OutfitFamily, fontWeight = FontWeight.ExtraBold, color = Color.White, fontSize = 26.sp, textAlign = TextAlign.Center), textAlign = TextAlign.Center)
        Text("Presiona el micrófono y cuéntame.", style = MaterialTheme.typography.bodyLarge.copy(color = Color.White.copy(alpha = 0.55f), fontSize = 16.sp, textAlign = TextAlign.Center, fontFamily = DmSansFamily), textAlign = TextAlign.Center)
        Row(horizontalArrangement = Arrangement.spacedBy(4.dp), verticalAlignment = Alignment.CenterVertically, modifier = Modifier.height(38.dp)) {
            heights.forEachIndexed { idx, maxH ->
                val wt = rememberInfiniteTransition(label = "w$idx")
                val h by wt.animateFloat(0.22f, if (recording) maxH else 0.22f, infiniteRepeatable(tween(1000, delayMillis = delays[idx]), RepeatMode.Reverse), label = "h$idx")
                Box(Modifier.width(4.dp).fillMaxSize(h).clip(RoundedCornerShape(2.dp)).background(if (recording) Teal else Color.White.copy(alpha = 0.2f)))
            }
        }
        Box(
            modifier = Modifier.size(130.dp).scale(if (recording) 1f else breathe)
                .drawBehind { if (recording) drawCircle(Red.copy(alpha = 0.4f * (1f - pulse)), radius = size.minDimension / 2f + 20f * pulse) }
                .clip(CircleShape).background(if (recording) Brush.linearGradient(listOf(Red, Color(0xFF922B21))) else Brush.linearGradient(listOf(Teal, BlueAccent)))
                .clickable {
                    if (!recording) { recording = true; scope.launch { delay(3000); transcription = "Me siento un poco cansado hoy, tuve dolor de cabeza."; recording = false } }
                },
            contentAlignment = Alignment.Center
        ) { Text(if (recording) "⏹" else "🎙️", fontSize = 54.sp) }
        Text(if (recording) "Te estoy escuchando…" else if (transcription.isNotEmpty()) "✓ Te escuché — guarda si es correcto" else "Toca para hablar", style = MaterialTheme.typography.headlineSmall.copy(fontFamily = OutfitFamily, fontWeight = FontWeight.Bold, color = Color.White.copy(alpha = 0.72f), fontSize = 18.sp, textAlign = TextAlign.Center), textAlign = TextAlign.Center)
        Box(Modifier.fillMaxWidth().clip(RoundedCornerShape(14.dp)).background(Color.White.copy(alpha = 0.06f)).border(1.5.dp, Color.White.copy(alpha = 0.12f), RoundedCornerShape(14.dp)).padding(15.dp)) {
            Text(if (transcription.isEmpty()) "Escuchando…" else transcription, style = MaterialTheme.typography.bodyLarge.copy(color = if (transcription.isEmpty()) Color.White.copy(alpha = 0.35f) else Color.White, fontFamily = DmSansFamily, fontSize = 16.sp, lineHeight = 24.sp, fontStyle = if (transcription.isEmpty()) FontStyle.Italic else FontStyle.Normal))
        }
        Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            listOf("😊 Bien hoy", "😓 Cansado", "😵 Mareo").forEach { label ->
                Box(Modifier.clip(RoundedCornerShape(20.dp)).background(Color.White.copy(alpha = 0.08f)).border(1.5.dp, Color.White.copy(alpha = 0.15f), RoundedCornerShape(20.dp)).clickable { transcription = label }.padding(horizontal = 20.dp, vertical = 10.dp)) {
                    Text(label, style = MaterialTheme.typography.titleLarge.copy(color = Color.White, fontFamily = OutfitFamily, fontWeight = FontWeight.Bold, fontSize = 16.sp))
                }
            }
        }
        if (transcription.isNotEmpty()) {
            Box(Modifier.fillMaxWidth().clip(RoundedCornerShape(12.dp)).background(Brush.linearGradient(listOf(Teal, BlueAccent))).clickable { scope.launch { snackbar.showSnackbar("✓ Guardado — La Dra. Peña lo verá pronto") } }.padding(vertical = 14.dp, horizontal = 30.dp), contentAlignment = Alignment.Center) {
                Text("✓ Guardar y listo", style = MaterialTheme.typography.titleLarge.copy(color = Color(0xFF060D18), fontFamily = OutfitFamily, fontWeight = FontWeight.ExtraBold, fontSize = 16.sp))
            }
        }
    }
}

@Composable
private fun ElderlyVitals() {
    Column(Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(18.dp), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(16.dp)) {
        Spacer(Modifier.height(8.dp))
        Text("Mis signos vitales hoy", style = MaterialTheme.typography.displayMedium.copy(fontFamily = OutfitFamily, fontWeight = FontWeight.Black, color = Color.White, fontSize = 24.sp, textAlign = TextAlign.Center), textAlign = TextAlign.Center)
        listOf(
            listOf(Triple("🩸", Pair("198", "mg/dL"), Triple("Glucosa", "⚠ Un poco alta", Amber)), Triple("❤️", Pair("74", "bpm"), Triple("Pulso", "✓ Normal", Green))),
            listOf(Triple("🫀", Pair("128/82", "mmHg"), Triple("Presión", "✓ Estable", Green)), Triple("🫁", Pair("97%", "SpO2"), Triple("Oxígeno", "✓ Bien", Green)))
        ).forEach { row ->
            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                row.forEach { (emoji, value, info) ->
                    Column(Modifier.weight(1f).clip(RoundedCornerShape(18.dp)).background(Color.White.copy(alpha = 0.06f)).border(2.dp, Color.White.copy(alpha = 0.09f), RoundedCornerShape(18.dp)).padding(24.dp), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(6.dp)) {
                        Text(emoji, fontSize = 40.sp)
                        Text(value.first, style = MaterialTheme.typography.displayMedium.copy(fontFamily = OutfitFamily, fontWeight = FontWeight.Black, color = Color.White, fontSize = 38.sp))
                        Text(value.second, style = MaterialTheme.typography.labelSmall.copy(color = Color.White.copy(alpha = 0.4f), fontSize = 12.sp))
                        Text(info.first, style = MaterialTheme.typography.titleSmall.copy(color = Color.White.copy(alpha = 0.5f), fontFamily = OutfitFamily, fontWeight = FontWeight.Bold, fontSize = 16.sp))
                        Text(info.second, style = MaterialTheme.typography.titleSmall.copy(color = info.third, fontWeight = FontWeight.ExtraBold, fontSize = 14.sp))
                    }
                }
            }
        }
        Text("Actualizado hace 4 minutos · sensor activo", style = MaterialTheme.typography.bodyMedium.copy(color = Color.White.copy(alpha = 0.4f), fontSize = 15.sp, fontFamily = DmSansFamily), textAlign = TextAlign.Center)
    }
}

@Composable
private fun ElderlySOS(snackbar: SnackbarHostState) {
    var showModal by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    val tr = rememberInfiniteTransition(label = "sos")
    val p1 by tr.animateFloat(18f, 26f, infiniteRepeatable(tween(3000, easing = EaseInOutSine), RepeatMode.Reverse), label = "p1")

    Column(Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(18.dp), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(22.dp)) {
        Spacer(Modifier.height(16.dp))
        Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.padding(horizontal = 16.dp)) {
            Text("Emergencia", style = MaterialTheme.typography.displayMedium.copy(fontFamily = OutfitFamily, fontWeight = FontWeight.ExtraBold, color = Color.White, fontSize = 24.sp))
            Spacer(Modifier.height(6.dp))
            Text("Al presionar, tu familia y médica son notificadas de inmediato.", style = MaterialTheme.typography.bodyLarge.copy(color = Color.White.copy(alpha = 0.55f), fontFamily = DmSansFamily, fontSize = 16.sp, textAlign = TextAlign.Center), textAlign = TextAlign.Center)
        }
        Box(Modifier.size(180.dp).drawBehind { drawCircle(Red.copy(alpha = 0.15f), radius = size.minDimension / 2f + p1) }.clip(CircleShape).background(Brush.linearGradient(listOf(Red, Color(0xFF922B21)))).clickable { showModal = true }, contentAlignment = Alignment.Center) {
            Text("SOS", style = MaterialTheme.typography.displayLarge.copy(fontFamily = OutfitFamily, fontWeight = FontWeight.Black, color = Color.White, fontSize = 28.sp))
        }
        Column(verticalArrangement = Arrangement.spacedBy(10.dp), modifier = Modifier.fillMaxWidth()) {
            listOf(Triple("Hospital Santa Fe", "1.2 km", "~4 min"), Triple("Clínica Colsanitas", "2.8 km", "~9 min")).forEach { (name, dist, time) ->
                Row(Modifier.fillMaxWidth().clip(RoundedCornerShape(15.dp)).background(Color.White.copy(alpha = 0.06f)).border(2.dp, Color.White.copy(alpha = 0.1f), RoundedCornerShape(15.dp)).clickable { scope.launch { snackbar.showSnackbar("🚑 Ruta activada → $name") } }.padding(14.dp), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                    Column {
                        Text(name, style = MaterialTheme.typography.titleLarge.copy(fontFamily = OutfitFamily, fontWeight = FontWeight.ExtraBold, color = Color.White, fontSize = 16.sp))
                        Text(dist, style = MaterialTheme.typography.bodyMedium.copy(color = Color.White.copy(alpha = 0.45f), fontSize = 13.sp))
                    }
                    Text(time, style = MaterialTheme.typography.displaySmall.copy(fontFamily = OutfitFamily, fontWeight = FontWeight.Black, color = Teal, fontSize = 19.sp))
                }
            }
        }
    }

    if (showModal) {
        AlertDialog(
            onDismissRequest = { showModal = false },
            title = { Text("🚨 Ayuda en camino", style = MaterialTheme.typography.headlineMedium.copy(fontFamily = OutfitFamily, fontWeight = FontWeight.ExtraBold, color = Color.White, fontSize = 23.sp)) },
            text = { Text("María fue notificada. Hospital Santa Fe · 4 minutos.", style = MaterialTheme.typography.bodyLarge.copy(color = Color.White.copy(alpha = 0.68f), fontFamily = DmSansFamily, fontSize = 16.sp)) },
            confirmButton = {},
            dismissButton = { TextButton(onClick = { showModal = false }) { Text("Cancelar", color = Teal, fontSize = 16.sp, fontWeight = FontWeight.Bold) } },
            containerColor = Color(0xFF183060)
        )
    }
}
