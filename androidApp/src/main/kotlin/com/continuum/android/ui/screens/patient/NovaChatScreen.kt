package com.continuum.android.ui.screens.patient

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.EaseInOutSine
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
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
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.continuum.android.ui.animation.AnimatedBlurOrb
import com.continuum.android.ui.animation.breatheAnimation
import com.continuum.android.ui.theme.Amber
import com.continuum.android.ui.theme.B1
import com.continuum.android.ui.theme.B2
import com.continuum.android.ui.theme.Bg1
import com.continuum.android.ui.theme.Bg3
import com.continuum.android.ui.theme.BlueAccent
import com.continuum.android.ui.theme.DmSansFamily
import com.continuum.android.ui.theme.Green
import com.continuum.android.ui.theme.MedRadius
import com.continuum.android.ui.theme.OutfitFamily
import com.continuum.android.ui.theme.T1
import com.continuum.android.ui.theme.T2
import com.continuum.android.ui.theme.T3
import com.continuum.android.ui.theme.T4
import com.continuum.android.ui.theme.Teal
import com.continuum.android.ui.theme.TealDim
import com.continuum.android.ui.theme.TealMid
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

data class ChatMessage(val text: String, val isNova: Boolean, val time: String)

@Composable
fun NovaChatScreen(
    modifier: Modifier = Modifier,
    snackbar: SnackbarHostState,
    onMicTap: () -> Unit
) {
    val messages = remember { mutableStateListOf<ChatMessage>() }
    var inputText by remember { mutableStateOf("") }
    var isTyping by remember { mutableStateOf(false) }
    var showQuickReplies by remember { mutableStateOf(true) }
    var showStreak by remember { mutableStateOf(false) }
    var showStatus by remember { mutableStateOf(false) }
    val listState = rememberLazyListState()
    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        delay(600)
        messages.add(ChatMessage("Buenos días, Carlos 👋 Son las 9:14 am del sábado. ¿Cómo amaneciste hoy?", isNova = true, time = "9:14 am"))
    }

    LaunchedEffect(messages.size) {
        if (messages.isNotEmpty()) {
            listState.animateScrollToItem(messages.size - 1)
        }
    }

    fun sendMessage(text: String) {
        if (text.isBlank()) return
        messages.add(ChatMessage(text, isNova = false, time = "9:15 am"))
        inputText = ""
        showQuickReplies = false
        isTyping = true
        scope.launch {
            delay(1800)
            isTyping = false
            val reply = when {
                text.contains("cansado", true) || text.contains("dolor", true) || text.contains("cabeza", true) ->
                    "Entiendo, Carlos. El cansancio después del almuerzo puede estar relacionado con tu glucosa. La guardé en tu historial. ¿Pudiste tomar la metformina hoy?"
                text.contains("medicamento", true) || text.contains("pastilla", true) || text.contains("tomé", true) ->
                    "¡Perfecto, Carlos! 💊 Eso suma +100 puntos. La Dra. Peña puede ver que estás siendo muy constante esta semana."
                text.contains("bien", true) || text.contains("sin síntoma", true) ->
                    "Me alegra escucharlo, Carlos 😊 Lo guardo en tu historial — estos días buenos son tan importantes como los difíciles para la Dra. Peña."
                text.contains("mareo", true) ->
                    "Gracias por contarme. El mareo al levantarse puede tener varios motivos. ¿Se te pasó rápido o duró un rato?"
                else ->
                    "Gracias por contarme, Carlos. Lo guardé en tu historial para que la Dra. Peña lo vea en tu próxima cita."
            }
            messages.add(ChatMessage(reply, isNova = true, time = "9:15 am"))
            if (!showStreak) {
                delay(400)
                showStreak = true
                showStatus = true
            }
        }
    }

    Box(modifier = modifier.fillMaxSize()) {
        AnimatedBlurOrb(color = Teal.copy(alpha = 0.12f), size = 320.dp, startX = -60f, startY = -60f, durationMs = 10000)
        AnimatedBlurOrb(color = BlueAccent.copy(alpha = 0.10f), size = 260.dp, startX = 800f, startY = 1200f, dxTarget = -18f, durationMs = 13000)

        Column(modifier = Modifier.fillMaxSize()) {
            LazyColumn(
                state = listState,
                modifier = Modifier.weight(1f),
                contentPadding = PaddingValues(bottom = 8.dp)
            ) {
                item { NovaGreetingHeader() }
                item { PazPinCard() }

                items(messages) { msg ->
                    AnimatedVisibility(
                        visible = true,
                        enter = fadeIn(tween(220)) + expandVertically()
                    ) {
                        ChatBubble(msg)
                    }
                }

                if (isTyping) {
                    item { TypingBubble() }
                }

                if (showStreak) {
                    item {
                        AnimatedVisibility(
                            visible = showStreak,
                            enter = fadeIn(tween(400)) + slideInVertically(tween(400)) { it / 2 }
                        ) {
                            StreakBanner()
                        }
                    }
                }

                if (showStatus) {
                    item {
                        AnimatedVisibility(
                            visible = showStatus,
                            enter = fadeIn(tween(300, delayMillis = 200)) + slideInVertically(tween(300, delayMillis = 200)) { it / 2 }
                        ) {
                            StatusCard()
                        }
                    }
                }
            }

            if (showQuickReplies) {
                LazyRow(
                    contentPadding = PaddingValues(horizontal = 18.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(7.dp)
                ) {
                    val replies = listOf("😓 Cansado", "💊 Tomé los medicamentos", "😊 Me siento bien", "😵 Tuve mareo")
                    items(replies) { reply ->
                        QuickReplyChip(text = reply, onClick = { sendMessage(reply) })
                    }
                }
            }

            ChatInputBar(
                text = inputText,
                onTextChange = { inputText = it },
                onSend = { sendMessage(inputText) },
                onMic = onMicTap
            )
        }
    }
}

@Composable
private fun NovaGreetingHeader() {
    val breathe by breatheAnimation()
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 28.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(88.dp)
                .scale(breathe)
                .clip(CircleShape)
                .background(Brush.linearGradient(listOf(Color(0xFF1E4A5A), Color(0xFF0D2535))))
                .border(2.dp, TealMid, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .size(54.dp)
                    .clip(CircleShape)
                    .background(Brush.linearGradient(listOf(Teal, BlueAccent))),
                contentAlignment = Alignment.Center
            ) {
                Text("🎙️", fontSize = 22.sp)
            }
        }
        Spacer(Modifier.height(18.dp))
        Text(
            text = "Nova · Asistente Continuum",
            style = MaterialTheme.typography.labelLarge.copy(
                fontFamily = OutfitFamily,
                color = Teal,
                fontWeight = FontWeight.Bold,
                fontSize = 13.sp
            )
        )
        Text(
            text = "Powered by Clinical Sieve AI",
            style = MaterialTheme.typography.labelSmall.copy(color = T3, fontSize = 11.sp)
        )
    }
}

@Composable
private fun PazPinCard() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 18.dp, vertical = 4.dp)
            .clip(RoundedCornerShape(MedRadius.dp))
            .background(Bg3)
            .border(1.dp, B1, RoundedCornerShape(MedRadius.dp))
            .padding(11.dp, 11.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(11.dp)
    ) {
        Row {
            Box(
                modifier = Modifier
                    .size(26.dp)
                    .clip(CircleShape)
                    .background(Brush.linearGradient(listOf(Color(0xFF8B5CF6), Color(0xFF6D28D9)))),
                contentAlignment = Alignment.Center
            ) { Text("AP", fontSize = 9.sp, fontWeight = FontWeight.Bold, color = Color.White) }
            Box(
                modifier = Modifier
                    .size(26.dp)
                    .offset((-6).dp, 0.dp)
                    .clip(CircleShape)
                    .background(Brush.linearGradient(listOf(Color(0xFFF59E0B), Color(0xFFD97706)))),
                contentAlignment = Alignment.Center
            ) { Text("MG", fontSize = 9.sp, fontWeight = FontWeight.Bold, color = Color.White) }
        }
        Text(
            text = "Dra. Peña y María están al tanto · Cita Mar 12 — resumen listo ✓",
            style = MaterialTheme.typography.bodySmall.copy(color = T2, lineHeight = 18.sp),
            fontSize = 12.sp
        )
    }
}

@Composable
private fun ChatBubble(msg: ChatMessage) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 18.dp, vertical = 4.dp),
        horizontalAlignment = if (msg.isNova) Alignment.Start else Alignment.End
    ) {
        Box(
            modifier = Modifier
                .widthIn(max = 300.dp)
                .clip(
                    RoundedCornerShape(
                        topStart = if (msg.isNova) 4.dp else MedRadius.dp,
                        topEnd = MedRadius.dp,
                        bottomStart = MedRadius.dp,
                        bottomEnd = if (msg.isNova) MedRadius.dp else 4.dp
                    )
                )
                .then(
                    if (msg.isNova)
                        Modifier.background(Bg3).border(1.dp, B1, RoundedCornerShape(topStart = 4.dp, topEnd = MedRadius.dp, bottomStart = MedRadius.dp, bottomEnd = MedRadius.dp))
                    else
                        Modifier.background(Brush.linearGradient(listOf(TealDim, BlueAccent.copy(alpha = 0.18f)))).border(1.dp, B2, RoundedCornerShape(topStart = MedRadius.dp, topEnd = MedRadius.dp, bottomStart = MedRadius.dp, bottomEnd = 4.dp))
                )
                .padding(12.dp, 12.dp)
        ) {
            Text(
                text = msg.text,
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = T1,
                    fontFamily = DmSansFamily,
                    fontSize = 13.5.sp,
                    lineHeight = 21.sp
                )
            )
        }
        Text(
            text = msg.time,
            style = MaterialTheme.typography.labelSmall.copy(color = T4, fontSize = 10.sp),
            modifier = Modifier.padding(horizontal = 2.dp, vertical = 2.dp)
        )
    }
}

@Composable
private fun TypingBubble() {
    val transition = rememberInfiniteTransition(label = "typing")
    val scales = (0..2).map { i ->
        transition.animateFloat(
            initialValue = 0f, targetValue = 1f,
            animationSpec = infiniteRepeatable(
                tween(1200, delayMillis = i * 200),
                RepeatMode.Restart
            ),
            label = "dot$i"
        ).value
    }
    Row(
        modifier = Modifier
            .padding(horizontal = 18.dp, vertical = 4.dp)
            .clip(RoundedCornerShape(topStart = 4.dp, topEnd = MedRadius.dp, bottomStart = MedRadius.dp, bottomEnd = MedRadius.dp))
            .background(Bg3)
            .border(1.dp, B1, RoundedCornerShape(topStart = 4.dp, topEnd = MedRadius.dp, bottomStart = MedRadius.dp, bottomEnd = MedRadius.dp))
            .padding(18.dp, 14.dp),
        horizontalArrangement = Arrangement.spacedBy(5.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        scales.forEach { scale ->
            Box(
                modifier = Modifier
                    .size(7.dp)
                    .scale(scale.coerceAtLeast(0.2f))
                    .clip(CircleShape)
                    .background(T3)
            )
        }
    }
}

@Composable
private fun StreakBanner() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 18.dp, vertical = 4.dp)
            .clip(RoundedCornerShape(MedRadius.dp))
            .background(Brush.linearGradient(listOf(TealDim, BlueAccent.copy(alpha = 0.08f))))
            .border(1.dp, B2, RoundedCornerShape(MedRadius.dp))
            .padding(13.dp, 13.dp)
            .clickable { },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text("🔥", fontSize = 28.sp)
        Column {
            Text(
                text = "¡6 días seguidos registrándote!",
                style = MaterialTheme.typography.titleSmall.copy(
                    color = Teal,
                    fontFamily = OutfitFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 13.sp
                )
            )
            Text(
                text = "La Dra. Peña tendrá un reporte muy completo para el martes. +50 pts →",
                style = MaterialTheme.typography.bodySmall.copy(color = T2, fontSize = 12.sp)
            )
        }
    }
}

@Composable
private fun StatusCard() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 18.dp, vertical = 4.dp)
            .clip(RoundedCornerShape(MedRadius.dp))
            .background(Bg3)
            .border(1.dp, B1, RoundedCornerShape(MedRadius.dp))
            .padding(14.dp, 14.dp)
    ) {
        listOf(
            Triple("🩸 Glucosa", "198 mg/dL", Pair("Un poco alta", Amber)),
            Triple("❤️ Pulso", "74 bpm", Pair("Normal", Green)),
            Triple("🫀 Presión", "128/82", Pair("Estable", Green))
        ).forEachIndexed { idx, (label, value, status) ->
            if (idx > 0) Divider(color = B1, thickness = 1.dp)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 7.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(label, style = MaterialTheme.typography.bodySmall.copy(color = T2, fontSize = 12.sp))
                Column(horizontalAlignment = Alignment.End) {
                    Text(
                        value,
                        style = MaterialTheme.typography.titleSmall.copy(
                            fontFamily = OutfitFamily,
                            fontWeight = FontWeight.Bold,
                            color = T1,
                            fontSize = 15.sp
                        )
                    )
                    Text(
                        status.first,
                        style = MaterialTheme.typography.labelSmall.copy(
                            color = status.second,
                            fontWeight = FontWeight.Bold,
                            fontSize = 10.5.sp
                        )
                    )
                }
            }
        }
    }
}

@Composable
private fun QuickReplyChip(text: String, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(20.dp))
            .background(Bg3)
            .border(1.dp, B1, RoundedCornerShape(20.dp))
            .clickable { onClick() }
            .padding(horizontal = 14.dp, vertical = 8.dp)
    ) {
        Text(text, style = MaterialTheme.typography.bodySmall.copy(color = T2, fontFamily = DmSansFamily, fontSize = 12.sp))
    }
}

@Composable
private fun ChatInputBar(
    text: String,
    onTextChange: (String) -> Unit,
    onSend: () -> Unit,
    onMic: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Bg1)
            .border(1.dp, B1, RoundedCornerShape(0.dp))
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Row(
            modifier = Modifier
                .weight(1f)
                .clip(RoundedCornerShape(30.dp))
                .background(Bg3)
                .border(1.dp, B1, RoundedCornerShape(30.dp))
                .padding(start = 16.dp, end = 8.dp, top = 4.dp, bottom = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextField(
                value = text,
                onValueChange = onTextChange,
                modifier = Modifier.weight(1f),
                placeholder = {
                    Text(
                        "Escribe o habla con Nova…",
                        style = MaterialTheme.typography.bodyMedium.copy(color = T4, fontSize = 13.5.sp, fontFamily = DmSansFamily)
                    )
                },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    focusedTextColor = T1,
                    unfocusedTextColor = T1,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    cursorColor = Teal
                ),
                textStyle = MaterialTheme.typography.bodyMedium.copy(
                    color = T1,
                    fontFamily = DmSansFamily,
                    fontSize = 13.5.sp
                ),
                singleLine = true,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Send),
                keyboardActions = KeyboardActions(onSend = { onSend() })
            )
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .clip(CircleShape)
                    .background(Brush.linearGradient(listOf(Teal, BlueAccent)))
                    .clickable { onMic() },
                contentAlignment = Alignment.Center
            ) {
                Text("🎙️", fontSize = 14.sp)
            }
        }
    }
}
