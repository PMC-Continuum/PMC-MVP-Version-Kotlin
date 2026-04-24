package com.continuum.presentation.dashboard

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun DashboardScreen(viewModel: DashboardViewModel) {
    val state by viewModel.state.collectAsState()

    if (state.isLoading) {
        Box(modifier = Modifier.fillMaxSize()) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }
        return
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Text("Tus tendencias", style = MaterialTheme.typography.headlineMedium)
            Text(
                "Últimos 30 días",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        // AI recommendation card
        state.analysis?.let { analysis ->
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer
                    )
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            "Recomendación IA",
                            style = MaterialTheme.typography.labelLarge,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Spacer(Modifier.height(8.dp))
                        Text(analysis.recommendation, style = MaterialTheme.typography.bodyMedium)
                    }
                }
            }

            if (analysis.worsening_patterns.isNotEmpty()) {
                item {
                    Text("Patrones en alerta", style = MaterialTheme.typography.titleMedium)
                    Spacer(Modifier.height(8.dp))
                    analysis.worsening_patterns.forEach { pattern ->
                        SuggestionChip(
                            onClick = {},
                            label = { Text(pattern) },
                            colors = SuggestionChipDefaults.suggestionChipColors(
                                containerColor = MaterialTheme.colorScheme.errorContainer
                            )
                        )
                        Spacer(Modifier.height(4.dp))
                    }
                }
            }

            if (analysis.improving_patterns.isNotEmpty()) {
                item {
                    Text("Mejorando", style = MaterialTheme.typography.titleMedium)
                    Spacer(Modifier.height(8.dp))
                    analysis.improving_patterns.forEach { pattern ->
                        SuggestionChip(
                            onClick = {},
                            label = { Text(pattern) },
                            colors = SuggestionChipDefaults.suggestionChipColors(
                                containerColor = MaterialTheme.colorScheme.secondaryContainer
                            )
                        )
                        Spacer(Modifier.height(4.dp))
                    }
                }
            }
        }

        // Entry count summary
        item {
            Card(modifier = Modifier.fillMaxWidth()) {
                Row(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            state.entries.size.toString(),
                            style = MaterialTheme.typography.displaySmall,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Text("registros", style = MaterialTheme.typography.labelMedium)
                    }
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        val avg = state.entries.mapNotNull { it.intensityScore }.average()
                        val avgStr = if (avg.isNaN()) "-" else {
                            val t = kotlin.math.round(avg * 10).toInt()
                            "${t / 10}.${t % 10}"
                        }
                        Text(
                            avgStr,
                            style = MaterialTheme.typography.displaySmall,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Text("intensidad media", style = MaterialTheme.typography.labelMedium)
                    }
                }
            }
        }

        state.error?.let {
            item { Text(it, color = MaterialTheme.colorScheme.error) }
        }
    }
}
