package com.continuum.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.continuum.ui.animation.AnimatedBlurOrb
import com.continuum.ui.theme.Amber
import com.continuum.ui.theme.B1
import com.continuum.ui.theme.B2
import com.continuum.ui.theme.Bg0
import com.continuum.ui.theme.Bg2
import com.continuum.ui.theme.BlueAccent
import com.continuum.ui.theme.LargeRadius
import com.continuum.ui.theme.OutfitFamily
import com.continuum.ui.theme.PurpleRole
import com.continuum.ui.theme.Rose
import com.continuum.ui.theme.T1
import com.continuum.ui.theme.T2
import com.continuum.ui.theme.Teal
import kotlinx.coroutines.delay

@Composable
fun RoleSelectorScreen(
    onPatient: () -> Unit,
    onDoctor: () -> Unit,
    onFamily: () -> Unit,
    onElderly: () -> Unit
) {
    var visible by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) { delay(80); visible = true }

    Box(modifier = Modifier.fillMaxSize().background(Bg0)) {
        AnimatedBlurOrb(color = Teal.copy(alpha = 0.18f), size = 440.dp, startX = -100f, startY = -110f, dxTarget = 28f, dyTarget = -18f, durationMs = 9000)
        AnimatedBlurOrb(color = BlueAccent.copy(alpha = 0.14f), size = 360.dp, startX = 900f, startY = 1400f, dxTarget = -22f, dyTarget = 20f, durationMs = 11000)
        AnimatedBlurOrb(color = Rose.copy(alpha = 0.10f), size = 280.dp, startX = 700f, startY = 700f, dxTarget = 18f, dyTarget = -12f, durationMs = 14000)

        Column(
            modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(horizontal = 20.dp, vertical = 40.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            AnimatedVisibility(visible = visible, enter = fadeIn(tween(280)) + slideInVertically(tween(280)) { it / 2 }) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(14.dp)) {
                        Box(
                            modifier = Modifier.size(52.dp).clip(RoundedCornerShape(16.dp)).background(Brush.linearGradient(listOf(Teal, BlueAccent))),
                            contentAlignment = Alignment.Center
                        ) { Text("∞", color = Color.White, fontSize = 22.sp, fontWeight = FontWeight.Bold) }
                        Text(
                            text = "Continuum",
                            style = TextStyle(
                                fontFamily = OutfitFamily,
                                fontWeight = FontWeight.ExtraBold,
                                fontSize = 34.sp,
                                letterSpacing = (-0.04).sp,
                                brush = Brush.horizontalGradient(listOf(Teal, Color.White))
                            )
                        )
                    }
                    Spacer(Modifier.height(10.dp))
                    Text("Tu salud no ocurre solo en las consultas.", style = MaterialTheme.typography.bodyLarge.copy(color = T2, textAlign = TextAlign.Center, fontWeight = FontWeight.SemiBold), textAlign = TextAlign.Center)
                    Text("Del cuidado reactivo al acompañamiento continuo e inteligente.", style = MaterialTheme.typography.bodyMedium.copy(color = T2, textAlign = TextAlign.Center), textAlign = TextAlign.Center)
                }
            }
            Spacer(Modifier.height(42.dp))
            AnimatedVisibility(visible = visible, enter = fadeIn(tween(280, delayMillis = 160)) + slideInVertically(tween(280, delayMillis = 160)) { it / 2 }) {
                Column(verticalArrangement = Arrangement.spacedBy(13.dp)) {
                    Row(horizontalArrangement = Arrangement.spacedBy(13.dp)) {
                        RoleCard("🧑‍💼", "Soy paciente", "Cuéntale cómo te sientes a Nova. Ella convierte tu relato en contexto clínico para tu médico.", "Paciente estándar", Teal, Modifier.weight(1f), onPatient)
                        RoleCard("👨‍⚕️", "Soy médico/a", "Recibe alertas, accede a reportes pre-consulta generados por IA y monitorea tu panel.", "Panel clínico", PurpleRole, Modifier.weight(1f), onDoctor)
                    }
                    Row(horizontalArrangement = Arrangement.spacedBy(13.dp)) {
                        RoleCard("👨‍👩‍👧", "Red de apoyo", "Monitorea a tu familiar sin llamar cada hora. Continuum te avisa si algo cambia.", "Familiar / cuidador", Amber, Modifier.weight(1f), onFamily)
                        RoleCard("👴", "Modo accesible", "Interfaz grande y sencilla. Habla con Nova con tu voz y recibe apoyo de tu médico.", "Adulto mayor", Teal, Modifier.weight(1f), onElderly)
                    }
                }
            }
        }
    }
}

@Composable
private fun RoleCard(emoji: String, name: String, description: String, chipText: String, accentColor: Color, modifier: Modifier = Modifier, onClick: () -> Unit) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val borderColor by animateColorAsState(if (isPressed) accentColor else B1, tween(260), label = "border")
    val overlayAlpha by animateColorAsState(if (isPressed) accentColor.copy(alpha = 0.07f) else Color.Transparent, tween(260), label = "overlay")

    Box(modifier = modifier.clip(RoundedCornerShape(LargeRadius.dp)).background(Bg2).border(1.5.dp, borderColor, RoundedCornerShape(LargeRadius.dp)).clickable(interactionSource = interactionSource, indication = null) { onClick() }) {
        Box(modifier = Modifier.matchParentSize().background(overlayAlpha))
        Column(modifier = Modifier.padding(22.dp, 26.dp)) {
            Text(emoji, fontSize = 42.sp)
            Spacer(Modifier.height(13.dp))
            Text(name, style = MaterialTheme.typography.titleLarge.copy(color = T1))
            Spacer(Modifier.height(5.dp))
            Text(description, style = MaterialTheme.typography.bodySmall.copy(color = T2, lineHeight = 18.sp))
            Spacer(Modifier.height(13.dp))
            Box(modifier = Modifier.clip(RoundedCornerShape(20.dp)).background(accentColor.copy(alpha = 0.16f)).border(1.dp, accentColor.copy(alpha = 0.30f), RoundedCornerShape(20.dp)).padding(horizontal = 10.dp, vertical = 3.dp)) {
                Text(chipText.uppercase(), style = MaterialTheme.typography.labelSmall.copy(color = accentColor, letterSpacing = 0.9.sp, fontWeight = FontWeight.Bold))
            }
        }
    }
}
