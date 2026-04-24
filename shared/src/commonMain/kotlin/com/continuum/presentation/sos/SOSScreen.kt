package com.continuum.presentation.sos

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun SOSScreen(
    viewModel: SOSViewModel,
    onBack: () -> Unit
) {
    val state by viewModel.state.collectAsState()

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        when (val s = state) {
            is SOSState.Idle -> {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(24.dp)
                ) {
                    Text("Emergencia", style = MaterialTheme.typography.headlineLarge)
                    Button(
                        onClick = { viewModel.initiateSOS() },
                        modifier = Modifier.size(160.dp),
                        shape = MaterialTheme.shapes.extraLarge,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.error
                        )
                    ) {
                        Text(
                            "SOS",
                            style = MaterialTheme.typography.displayMedium,
                            color = MaterialTheme.colorScheme.onError
                        )
                    }
                    TextButton(onClick = onBack) { Text("Cancelar") }
                }
            }

            is SOSState.Countdown -> {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(24.dp)
                ) {
                    Text("¿Confirmar emergencia?", style = MaterialTheme.typography.headlineSmall)
                    Text(
                        "Se enviará tu historial clínico al equipo de respuesta.",
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        OutlinedButton(
                            onClick = { viewModel.cancel() },
                            modifier = Modifier.weight(1f)
                        ) { Text("Cancelar") }
                        Button(
                            onClick = { viewModel.confirmSOS() },
                            modifier = Modifier.weight(1f),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.error
                            )
                        ) { Text("Confirmar SOS") }
                    }
                }
            }

            is SOSState.Sending -> {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    CircularProgressIndicator(color = MaterialTheme.colorScheme.error)
                    Text("Enviando resumen clínico...", style = MaterialTheme.typography.bodyLarge)
                }
            }

            is SOSState.Sent -> {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Text(
                        "✓ Alerta enviada",
                        style = MaterialTheme.typography.headlineSmall,
                        color = MaterialTheme.colorScheme.primary
                    )
                    s.event.nearestHospital?.let {
                        Text("Hospital más cercano: $it", style = MaterialTheme.typography.bodyMedium)
                    }
                    Text(s.event.clinicalSummary, style = MaterialTheme.typography.bodySmall)
                    Button(onClick = { viewModel.reset(); onBack() }) { Text("Volver al inicio") }
                }
            }

            is SOSState.Error -> {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Text(
                        "Error al enviar SOS",
                        style = MaterialTheme.typography.headlineSmall,
                        color = MaterialTheme.colorScheme.error
                    )
                    Text(s.message, style = MaterialTheme.typography.bodyMedium)
                    Button(onClick = { viewModel.reset() }) { Text("Reintentar") }
                }
            }
        }
    }
}
