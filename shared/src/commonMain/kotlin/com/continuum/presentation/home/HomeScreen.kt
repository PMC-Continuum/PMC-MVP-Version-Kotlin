package com.continuum.presentation.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    onNavigateToVoiceEntry: () -> Unit,
    onNavigateToDashboard: () -> Unit,
    onNavigateToSOS: () -> Unit
) {
    val state by viewModel.state.collectAsState()

    Box(modifier = Modifier.fillMaxSize()) {
        if (state.isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Greeting
                item {
                    Text(
                        text = "Buenos días, ${state.profile?.fullName ?: ""}",
                        style = MaterialTheme.typography.headlineMedium
                    )
                    Text(
                        text = "¿Cómo te sientes hoy?",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                // Record CTA
                item {
                    Button(
                        onClick = onNavigateToVoiceEntry,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(64.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary
                        )
                    ) {
                        Text("Registrar síntoma de hoy", style = MaterialTheme.typography.titleMedium)
                    }
                }

                // Recent entries
                if (state.recentEntries.isNotEmpty()) {
                    item {
                        Text("Últimas entradas", style = MaterialTheme.typography.titleMedium)
                    }
                    items(state.recentEntries) { entry ->
                        Card(modifier = Modifier.fillMaxWidth()) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                entry.aiSummary?.let { summary ->
                                    Text(summary, style = MaterialTheme.typography.bodyMedium)
                                }
                                entry.intensityScore?.let { score ->
                                    Text(
                                        "Intensidad: $score/10",
                                        style = MaterialTheme.typography.labelMedium,
                                        color = MaterialTheme.colorScheme.primary
                                    )
                                }
                            }
                        }
                    }
                }

                // Dashboard link
                item {
                    OutlinedButton(
                        onClick = onNavigateToDashboard,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Ver tendencias de salud →")
                    }
                }

                // Error
                state.error?.let { err ->
                    item {
                        Card(
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.errorContainer
                            )
                        ) {
                            Text(
                                err,
                                modifier = Modifier.padding(16.dp),
                                color = MaterialTheme.colorScheme.onErrorContainer
                            )
                        }
                    }
                }
            }
        }

        // Persistent SOS button
        FloatingActionButton(
            onClick = onNavigateToSOS,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(24.dp),
            containerColor = MaterialTheme.colorScheme.error
        ) {
            Text("SOS", color = MaterialTheme.colorScheme.onError)
        }
    }
}
