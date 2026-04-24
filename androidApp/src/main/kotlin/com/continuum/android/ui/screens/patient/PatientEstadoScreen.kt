package com.continuum.android.ui.screens.patient

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.continuum.android.ui.theme.Amber
import com.continuum.android.ui.theme.B1
import com.continuum.android.ui.theme.Bg2
import com.continuum.android.ui.theme.Bg3
import com.continuum.android.ui.theme.BlueAccent
import com.continuum.android.ui.theme.DmSansFamily
import com.continuum.android.ui.theme.Green
import com.continuum.android.ui.theme.MedRadius
import com.continuum.android.ui.theme.OutfitFamily
import com.continuum.android.ui.theme.T1
import com.continuum.android.ui.theme.T2
import com.continuum.android.ui.theme.T3
import com.continuum.android.ui.theme.Teal

private data class VitalData(
    val emoji: String,
    val value: String,
    val unit: String,
    val name: String,
    val status: String,
    val statusColor: Color,
    val isAlert: Boolean,
    val bars: List<Float>
)

@Composable
fun PatientEstadoScreen(modifier: Modifier = Modifier, snackbar: SnackbarHostState) {
    val vitals = listOf(
        VitalData("🩸", "198", "mg/dL", "Glucosa", "Un poco alta", Amber, true,
            listOf(0.38f, 0.52f, 0.42f, 0.62f, 0.46f, 0.58f, 0.80f)),
        VitalData("🫀", "128/82", "", "Presión", "Estable", Green, false,
            listOf(0.52f, 0.55f, 0.53f, 0.58f, 0.54f, 0.57f, 0.56f)),
        VitalData("❤️", "74", "bpm", "Frecuencia cardíaca", "Normal", Green, false,
            listOf(0.55f, 0.60f, 0.58f, 0.64f, 0.56f, 0.62f, 0.59f)),
        VitalData("🫁", "97", "%", "SpO2", "Bien", Green, false,
            listOf(0.88f, 0.92f, 0.90f, 0.95f, 0.89f, 0.93f, 0.91f))
    )

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        Text(
            text = "Mi estado de hoy",
            style = MaterialTheme.typography.displaySmall.copy(
                fontFamily = OutfitFamily,
                fontWeight = FontWeight.ExtraBold,
                color = T1,
                letterSpacing = (-0.02).sp
            )
        )
        Text(
            text = "Sábado 7 de marzo · Signos vitales actualizados hace 4 min",
            style = MaterialTheme.typography.bodySmall.copy(color = T3, fontFamily = DmSansFamily)
        )

        Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            VitalCard(vital = vitals[0], modifier = Modifier.weight(1f))
            VitalCard(vital = vitals[1], modifier = Modifier.weight(1f))
        }
        Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            VitalCard(vital = vitals[2], modifier = Modifier.weight(1f))
            VitalCard(vital = vitals[3], modifier = Modifier.weight(1f))
        }

        MedicationCard()
        ContinuityStoryCard()
    }
}

@Composable
private fun VitalCard(vital: VitalData, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(MedRadius.dp))
            .background(Bg2)
            .border(1.dp, if (vital.isAlert) Amber.copy(alpha = 0.30f) else B1, RoundedCornerShape(MedRadius.dp))
            .clickable { }
            .padding(14.dp)
    ) {
        Text(vital.emoji, fontSize = 22.sp)
        Spacer(Modifier.height(6.dp))
        Row(verticalAlignment = Alignment.Bottom) {
            Text(
                text = vital.value,
                style = MaterialTheme.typography.displaySmall.copy(
                    fontFamily = OutfitFamily,
                    fontWeight = FontWeight.ExtraBold,
                    color = T1,
                    fontSize = 24.sp
                )
            )
            if (vital.unit.isNotEmpty()) {
                Text(
                    text = vital.unit,
                    style = MaterialTheme.typography.labelSmall.copy(color = T3, fontSize = 11.sp),
                    modifier = Modifier.padding(start = 3.dp, bottom = 3.dp)
                )
            }
        }
        Text(vital.name, style = MaterialTheme.typography.labelSmall.copy(color = T2, fontSize = 11.sp))
        Text(
            vital.status,
            style = MaterialTheme.typography.labelSmall.copy(
                color = vital.statusColor,
                fontWeight = FontWeight.Bold,
                fontSize = 11.sp
            )
        )
        Spacer(Modifier.height(8.dp))
        Row(
            horizontalArrangement = Arrangement.spacedBy(2.dp),
            verticalAlignment = Alignment.Bottom,
            modifier = Modifier.fillMaxWidth().height(24.dp)
        ) {
            vital.bars.forEach { h ->
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight(h)
                        .clip(RoundedCornerShape(topStart = 2.dp, topEnd = 2.dp))
                        .background(
                            if (vital.isAlert) Amber.copy(alpha = 0.85f)
                            else Teal.copy(alpha = 0.35f)
                        )
                )
            }
        }
    }
}

@Composable
private fun MedicationCard() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(MedRadius.dp))
            .background(Bg2)
            .border(1.dp, B1, RoundedCornerShape(MedRadius.dp))
            .clickable { }
            .padding(15.dp, 15.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(13.dp)
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(Green.copy(alpha = 0.15f)),
            contentAlignment = Alignment.Center
        ) {
            Text("💊", fontSize = 20.sp)
        }
        Column(modifier = Modifier.weight(1f)) {
            Text(
                "Metformina 850 mg",
                style = MaterialTheme.typography.labelLarge.copy(
                    color = T1,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 13.sp,
                    fontFamily = DmSansFamily
                )
            )
            Text(
                "Esta mañana con el desayuno",
                style = MaterialTheme.typography.labelSmall.copy(color = T2, fontSize = 11.sp)
            )
        }
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(20.dp))
                .background(Green.copy(alpha = 0.15f))
                .padding(horizontal = 10.dp, vertical = 4.dp)
        ) {
            Text(
                "✓ Tomada hoy",
                style = MaterialTheme.typography.labelSmall.copy(
                    color = Green,
                    fontWeight = FontWeight.Bold,
                    fontSize = 11.sp
                )
            )
        }
    }
}

@Composable
private fun ContinuityStoryCard() {
    val dayColors = listOf(Green, Green, Amber, Green, Green, Green, Amber, Green, Green, Green, Green, Green, Green, Teal)
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(MedRadius.dp))
            .background(Bg2)
            .border(1.dp, B1, RoundedCornerShape(MedRadius.dp))
            .padding(15.dp, 15.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                "🔥 Racha de registros",
                style = MaterialTheme.typography.labelLarge.copy(
                    fontWeight = FontWeight.SemiBold,
                    color = T1,
                    fontSize = 13.sp,
                    fontFamily = DmSansFamily
                )
            )
            Text(
                "últimos 14 días",
                style = MaterialTheme.typography.labelSmall.copy(color = T3, fontSize = 11.sp)
            )
        }
        Row(horizontalArrangement = Arrangement.spacedBy(3.dp)) {
            dayColors.forEach { color ->
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(16.dp)
                        .clip(RoundedCornerShape(3.dp))
                        .background(color.copy(alpha = 0.75f))
                )
            }
        }
        Text(
            text = "¡Llevas 6 días seguidos! Tu médica tendrá un reporte muy completo para el martes.",
            style = MaterialTheme.typography.bodySmall.copy(
                color = T2,
                fontFamily = DmSansFamily,
                lineHeight = 17.sp,
                fontSize = 12.sp
            )
        )
        LinearProgressIndicator(
            progress = { 0.87f },
            modifier = Modifier.fillMaxWidth().height(4.dp).clip(RoundedCornerShape(2.dp)),
            color = Teal,
            trackColor = Bg3,
            strokeCap = StrokeCap.Round
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                "Adherencia semana: 87%",
                style = MaterialTheme.typography.labelSmall.copy(color = T2, fontSize = 11.sp, fontFamily = DmSansFamily)
            )
            Text(
                "1 240 pts 🏆",
                style = MaterialTheme.typography.labelSmall.copy(
                    color = Teal,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 11.sp
                ),
                modifier = Modifier.clickable { }
            )
        }
    }
}
