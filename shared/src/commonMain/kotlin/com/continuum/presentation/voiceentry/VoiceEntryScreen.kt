package com.continuum.presentation.voiceentry

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun VoiceEntryScreen(
    viewModel: VoiceEntryViewModel,
    onDone: () -> Unit
) {
    val state by viewModel.state.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        when (val s = state) {
            is VoiceEntryState.Idle -> {
                Text("Habla sobre cómo te sientes hoy", style = MaterialTheme.typography.headlineSmall)
                Spacer(Modifier.height(32.dp))
                Button(
                    onClick = { viewModel.startRecording() },
                    modifier = Modifier.size(120.dp),
                    shape = MaterialTheme.shapes.extraLarge
                ) {
                    Text("●", style = MaterialTheme.typography.displayMedium)
                }
                Spacer(Modifier.height(16.dp))
                Text("Toca para grabar", style = MaterialTheme.typography.bodyMedium)
            }

            is VoiceEntryState.Recording -> {
                Text(
                    "Grabando...",
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.error
                )
                Spacer(Modifier.height(32.dp))
                Button(
                    onClick = { viewModel.stopAndProcess() },
                    modifier = Modifier.size(120.dp),
                    shape = MaterialTheme.shapes.extraLarge,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.error
                    )
                ) {
                    Text("■", style = MaterialTheme.typography.displayMedium)
                }
                Spacer(Modifier.height(16.dp))
                Text("Toca para detener", style = MaterialTheme.typography.bodyMedium)
            }

            is VoiceEntryState.Processing -> {
                CircularProgressIndicator()
                Spacer(Modifier.height(16.dp))
                Text("Analizando con IA...", style = MaterialTheme.typography.bodyLarge)
            }

            is VoiceEntryState.Review -> {
                Text("Revisión", style = MaterialTheme.typography.headlineSmall)
                Spacer(Modifier.height(16.dp))
                Card(modifier = Modifier.fillMaxWidth()) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        s.entry.aiSummary?.let { Text(it, style = MaterialTheme.typography.bodyLarge) }
                        Text("Síntomas: ${s.entry.symptoms.joinToString(", ")}")
                        s.entry.intensityScore?.let { Text("Intensidad: $it/10") }
                    }
                }
                Spacer(Modifier.height(24.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    OutlinedButton(
                        onClick = { viewModel.reset() },
                        modifier = Modifier.weight(1f)
                    ) { Text("Repetir") }
                    Button(
                        onClick = { viewModel.save() },
                        modifier = Modifier.weight(1f)
                    ) { Text("Guardar") }
                }
            }

            is VoiceEntryState.Saved -> {
                Text(
                    "✓ Guardado",
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(Modifier.height(8.dp))
                Text(s.entry.aiSummary ?: "", style = MaterialTheme.typography.bodyMedium)
                Spacer(Modifier.height(24.dp))
                Button(onClick = onDone) { Text("Volver al inicio") }
            }

            is VoiceEntryState.Error -> {
                Text(
                    "Error",
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.error
                )
                Spacer(Modifier.height(8.dp))
                Text(s.message, style = MaterialTheme.typography.bodyMedium)
                Spacer(Modifier.height(24.dp))
                Button(onClick = { viewModel.reset() }) { Text("Reintentar") }
            }
        }
    }
}
