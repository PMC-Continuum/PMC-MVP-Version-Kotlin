package com.continuum.android.ui.screens.doctor

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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.continuum.android.ui.theme.Amber
import com.continuum.android.ui.theme.B1
import com.continuum.android.ui.theme.Bg2
import com.continuum.android.ui.theme.DmSansFamily
import com.continuum.android.ui.theme.Green
import com.continuum.android.ui.theme.MedRadius
import com.continuum.android.ui.theme.OutfitFamily
import com.continuum.android.ui.theme.Red
import com.continuum.android.ui.theme.T1
import com.continuum.android.ui.theme.T2
import com.continuum.android.ui.theme.T3
import com.continuum.android.ui.theme.Teal

private data class PatientRow(
    val initials: String,
    val name: String,
    val condition: String,
    val alert: String,
    val alertColor: Color,
    val urgency: String,
    val nextAppt: String,
    val hasPreConsult: Boolean,
    val isClickable: Boolean = false
)

@Composable
fun DoctorPatientListScreen(
    modifier: Modifier = Modifier,
    onPatientClick: () -> Unit,
    snackbar: SnackbarHostState
) {
    val patients = listOf(
        PatientRow("CM", "Carlos Méndez", "DM2 + HTA", "Glucosa alta · 198 mg/dL", Red, "urgent", "Mar 12 · Martes", true, isClickable = true),
        PatientRow("LR", "Lucía Rodríguez", "DM2 + Cardiopatía", "Glucosa crítica · 267 mg/dL", Red, "urgent", "Mar 11 · Lunes", false),
        PatientRow("JA", "Jorge Ávila", "HTA + IRC", "Presión 145/92 mmHg", Amber, "warn", "Mar 14 · Jueves", true),
        PatientRow("RE", "Rebeca Estrada", "DM2", "Adherencia 58% esta semana", Amber, "warn", "Mar 15 · Viernes", false),
        PatientRow("SM", "Santiago Mora", "HTA", "Control estable · 7 días", Green, "ok", "Mar 20 · Mié", true)
    )

    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = androidx.compose.foundation.layout.PaddingValues(20.dp),
        verticalArrangement = Arrangement.spacedBy(9.dp)
    ) {
        item {
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text("Mis pacientes crónicos", style = MaterialTheme.typography.headlineLarge.copy(fontFamily = OutfitFamily, fontWeight = FontWeight.ExtraBold, color = T1))
                Text("Ordenados por urgencia clínica · Clinical Sieve AI", style = MaterialTheme.typography.bodySmall.copy(color = T3, fontFamily = DmSansFamily))
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.padding(top = 8.dp)) {
                    UrgencyTag("● 2 urgentes", Red)
                    UrgencyTag("● 2 atención", Amber)
                    UrgencyTag("● 1 estable", Green)
                }
            }
        }
        items(patients) { patient ->
            PatientRowCard(patient = patient, onClick = { if (patient.isClickable) onPatientClick() })
        }
    }
}

@Composable
private fun UrgencyTag(text: String, color: Color) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(20.dp))
            .background(color.copy(alpha = 0.15f))
            .padding(horizontal = 10.dp, vertical = 3.dp)
    ) {
        Text(text, style = MaterialTheme.typography.labelSmall.copy(color = color, fontWeight = FontWeight.Bold, fontSize = 10.5.sp, fontFamily = DmSansFamily))
    }
}

@Composable
private fun PatientRowCard(patient: PatientRow, onClick: () -> Unit) {
    val borderColor = when (patient.urgency) {
        "urgent" -> Red.copy(alpha = 0.30f)
        "warn"   -> Amber.copy(alpha = 0.25f)
        else     -> B1
    }
    val bgTint = when (patient.urgency) {
        "urgent" -> Red.copy(alpha = 0.04f)
        "warn"   -> Amber.copy(alpha = 0.04f)
        else     -> Color.Transparent
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(MedRadius.dp))
            .background(Bg2)
            .background(bgTint)
            .border(1.dp, borderColor, RoundedCornerShape(MedRadius.dp))
            .clickable { onClick() }
            .padding(14.dp, 14.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(13.dp)
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .background(patient.alertColor.copy(alpha = 0.2f)),
            contentAlignment = Alignment.Center
        ) {
            Text(patient.initials, style = MaterialTheme.typography.labelLarge.copy(color = patient.alertColor, fontWeight = FontWeight.Bold, fontSize = 13.sp))
        }
        Column(modifier = Modifier.weight(1f)) {
            Text(patient.name, style = MaterialTheme.typography.titleSmall.copy(fontFamily = OutfitFamily, fontWeight = FontWeight.Bold, color = T1, fontSize = 13.5.sp))
            Text(patient.condition, style = MaterialTheme.typography.labelSmall.copy(color = T2, fontSize = 11.sp))
            Text(patient.alert, style = MaterialTheme.typography.labelSmall.copy(color = patient.alertColor, fontWeight = FontWeight.SemiBold, fontSize = 10.5.sp))
        }
        Column(horizontalAlignment = Alignment.End, verticalArrangement = Arrangement.spacedBy(3.dp)) {
            Box(modifier = Modifier.size(8.dp).clip(CircleShape).background(patient.alertColor))
            Text(patient.nextAppt, style = MaterialTheme.typography.labelSmall.copy(color = T3, fontSize = 10.sp))
            if (patient.hasPreConsult) {
                Text("Pre-consulta ✓", style = MaterialTheme.typography.labelSmall.copy(color = Teal, fontWeight = FontWeight.SemiBold, fontSize = 10.sp))
            }
        }
    }
}
