package com.continuum.ui.screens.patient

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.continuum.ui.theme.B1
import com.continuum.ui.theme.Bg0
import com.continuum.ui.theme.Bg3
import com.continuum.ui.theme.BlueAccent
import com.continuum.ui.theme.DmSansFamily
import com.continuum.ui.theme.MedRadius
import com.continuum.ui.theme.OutfitFamily
import com.continuum.ui.theme.Red
import com.continuum.ui.theme.T1
import com.continuum.ui.theme.T2
import com.continuum.ui.theme.T3
import com.continuum.ui.theme.Teal
import com.continuum.ui.theme.TealMid
import kotlinx.coroutines.delay

private val sampleTranscriptions = listOf(
    "Me siento un poco cansado, tuve dolor de cabeza después del almuerzo.",
    "Tomé la metformina esta mañana con el desayuno.",
    "Amanecí bien hoy, sin ningún síntoma especial.",
    "Tuve un mareo al levantarme, duró como un minuto.",
    "El pulso se siente un poco acelerado pero ya se me pasó."
)

@Composable
fun VoiceOverlayScreen(onDismiss: () -> Unit) {
    var recording by remember { mutableStateOf(true) }
    var transcription by remember { mutableStateOf("") }
    var showActions by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) { delay(2800); transcription = sampleTranscriptions.random(); showActions = true }

    Dialog(onDismissRequest = onDismiss, properties = DialogProperties(usePlatformDefaultWidth = false)) {
        Box(Modifier.fillMaxSize().background(Bg0.copy(alpha = 0.88f)), contentAlignment = Alignment.Center) {
            Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(24.dp), modifier = Modifier.padding(horizontal = 24.dp)) {
                Text("Nova te está escuchando", style = MaterialTheme.typography.displaySmall.copy(fontFamily = OutfitFamily, fontWeight = FontWeight.Bold, color = T1, textAlign = TextAlign.Center))
                Text("Habla con naturalidad — síntomas, medicamentos…", style = MaterialTheme.typography.bodyLarge.copy(color = T2, fontFamily = DmSansFamily, textAlign = TextAlign.Center), textAlign = TextAlign.Center)
                WaveVisualizer(recording)
                VoiceOrb(recording) { recording = false }
                Box(Modifier.fillMaxWidth().clip(RoundedCornerShape(MedRadius.dp)).background(Bg3).border(1.dp, B1, RoundedCornerShape(MedRadius.dp)).padding(18.dp, 14.dp)) {
                    Text(if (transcription.isEmpty()) "Escuchando…" else transcription,
                        style = MaterialTheme.typography.bodyLarge.copy(color = if (transcription.isEmpty()) T3 else T1, fontFamily = DmSansFamily, lineHeight = 24.sp, fontStyle = if (transcription.isEmpty()) FontStyle.Italic else FontStyle.Normal))
                }
                AnimatedVisibility(showActions, enter = fadeIn(tween(300))) {
                    Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                        Box(Modifier.weight(1f).clip(RoundedCornerShape(10.dp)).border(1.dp, TealMid, RoundedCornerShape(10.dp)).clickable { onDismiss() }.padding(vertical = 10.dp), contentAlignment = Alignment.Center) {
                            Text("Descartar", style = MaterialTheme.typography.labelLarge.copy(color = T2))
                        }
                        Box(Modifier.weight(1.5f).clip(RoundedCornerShape(10.dp)).background(Brush.linearGradient(listOf(Teal, BlueAccent))).clickable { onDismiss() }.padding(vertical = 10.dp), contentAlignment = Alignment.Center) {
                            Text("Guardar en historial ✓", style = MaterialTheme.typography.labelLarge.copy(color = Bg0, fontWeight = FontWeight.Bold))
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun WaveVisualizer(recording: Boolean) {
    val transition = rememberInfiniteTransition(label = "waves")
    val delays = listOf(0, 100, 200, 300, 400, 300, 200, 100)
    val heights = listOf(0.28f, 0.60f, 0.90f, 0.70f, 0.50f, 0.78f, 0.55f, 0.38f)
    Row(horizontalArrangement = Arrangement.spacedBy(4.dp), verticalAlignment = Alignment.CenterVertically, modifier = Modifier.height(36.dp)) {
        heights.forEachIndexed { idx, maxH ->
            val scale by transition.animateFloat(0.22f, if (recording) maxH else 0.22f, infiniteRepeatable(tween(1000, delayMillis = delays[idx]), RepeatMode.Reverse), label = "w$idx")
            Box(Modifier.width(4.dp).fillMaxSize(scale).clip(RoundedCornerShape(2.dp)).background(if (recording) Teal else T3))
        }
    }
}

@Composable
private fun VoiceOrb(recording: Boolean, onStop: () -> Unit) {
    Box(
        modifier = Modifier.size(120.dp).clip(CircleShape)
            .background(if (recording) Brush.linearGradient(listOf(Color(0xFF1E4A5A), Color(0xFF0D2535))) else Brush.linearGradient(listOf(Teal.copy(alpha = 0.3f), BlueAccent.copy(alpha = 0.3f))))
            .border(2.5.dp, TealMid, CircleShape),
        contentAlignment = Alignment.Center
    ) {
        Box(Modifier.size(74.dp).clip(CircleShape).background(if (recording) Brush.linearGradient(listOf(Red, Color(0xFF922B21))) else Brush.linearGradient(listOf(Teal, BlueAccent))).clickable { onStop() }, contentAlignment = Alignment.Center) {
            Text(if (recording) "⏹" else "✓", fontSize = 26.sp)
        }
    }
}
