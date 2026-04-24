package com.continuum.presentation.landing

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun LandingScreen(
    onJoinPilot: (name: String, condition: String, city: String, email: String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        HeroSection(onScrollToPilot = {})
        ProblemSection()
        HowItWorksSection()
        ForWhomSection()
        ResultsSection()
        PilotFormSection(onSubmit = onJoinPilot)
    }
}

// ── Hero ─────────────────────────────────────────────────────────────────────

@Composable
private fun HeroSection(onScrollToPilot: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF0D1117))
            .padding(horizontal = 24.dp, vertical = 80.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            Text(
                text = "Tu médico necesita saber lo que te pasa entre citas.\nContinuum lo captura por ti.",
                style = MaterialTheme.typography.displaySmall.copy(
                    color = Color.White,
                    fontStyle = FontStyle.Normal
                ),
                textAlign = androidx.compose.ui.text.style.TextAlign.Center
            )
            Text(
                text = "Registra tus síntomas por voz. La IA los estructura.\nTu médico llega a la cita ya informado.",
                style = MaterialTheme.typography.bodyLarge.copy(color = Color(0xFF9CA3AF)),
                textAlign = androidx.compose.ui.text.style.TextAlign.Center
            )
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                Button(
                    onClick = onScrollToPilot,
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0F6E56))
                ) {
                    Text("Únete al piloto gratuito →")
                }
                OutlinedButton(onClick = {}) {
                    Text("Ver cómo funciona ↓", color = Color.White)
                }
            }
            Row(
                horizontalArrangement = Arrangement.spacedBy(32.dp),
                modifier = Modifier.padding(top = 16.dp)
            ) {
                ProofItem("10", "entrevistas validadas")
                ProofItem("15M+", "colombianos con condición crónica")
                ProofItem("2026", "piloto activo en curso")
            }
        }
    }
}

@Composable
private fun ProofItem(number: String, label: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            number, style = MaterialTheme.typography.headlineMedium.copy(
                color = Color(0xFF1D9E75), fontWeight = FontWeight.W500
            )
        )
        Text(
            label, style = MaterialTheme.typography.labelSmall.copy(color = Color(0xFF6B7280)),
            textAlign = androidx.compose.ui.text.style.TextAlign.Center
        )
    }
}

// ── Problem ───────────────────────────────────────────────────────────────────

@Composable
private fun ProblemSection() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF161B22))
            .padding(horizontal = 24.dp, vertical = 64.dp),
        verticalArrangement = Arrangement.spacedBy(32.dp)
    ) {
        SectionLabel("El problema")
        Text(
            "El sistema de salud actúa cuando el daño ya está hecho.",
            style = MaterialTheme.typography.headlineMedium.copy(color = Color.White)
        )
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            StatCard("70%", "de la información vital se pierde entre consultas", Modifier.weight(1f))
            StatCard(
                "1 de 4", "muertes en Colombia es por enfermedad cardiovascular evitable",
                Modifier.weight(1f)
            )
            StatCard("15M+", "colombianos con al menos una condición crónica sin monitoreo", Modifier.weight(1f))
        }
        Text(
            "\"Estamos actuando cuando el daño ya está hecho, no antes de que ocurra.\"",
            style = MaterialTheme.typography.bodyLarge.copy(
                color = Color(0xFF6B7280), fontStyle = FontStyle.Italic
            )
        )
    }
}

@Composable
private fun StatCard(number: String, desc: String, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(containerColor = Color(0xFF21262D))
    ) {
        Column(modifier = Modifier.padding(20.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text(
                number, style = MaterialTheme.typography.displaySmall.copy(
                    color = Color.White, fontWeight = FontWeight.W400
                )
            )
            Text(desc, style = MaterialTheme.typography.bodySmall.copy(color = Color(0xFF9CA3AF)))
        }
    }
}

// ── How it works ──────────────────────────────────────────────────────────────

@Composable
private fun HowItWorksSection() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 64.dp),
        verticalArrangement = Arrangement.spacedBy(32.dp)
    ) {
        SectionLabel("Cómo funciona")
        Text("Tres pasos simples. Una diferencia enorme.", style = MaterialTheme.typography.headlineMedium)
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            StepCard(
                "01", "Habla",
                "Graba 30 segundos cada día diciendo cómo te sientes. Sin formularios, sin apps complicadas.",
                Modifier.weight(1f)
            )
            StepCard(
                "02", "Monitorea",
                "Continuum estructura tus síntomas con IA y detecta patrones antes de que se vuelvan crisis.",
                Modifier.weight(1f)
            )
            StepCard(
                "03", "Llega preparado",
                "Antes de cada cita, tu médico recibe un resumen clínico de las últimas semanas. Sin papel. Sin olvidos.",
                Modifier.weight(1f)
            )
        }
    }
}

@Composable
private fun StepCard(number: String, title: String, desc: String, modifier: Modifier = Modifier) {
    Card(modifier = modifier) {
        Column(modifier = Modifier.padding(24.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
            Text(number, style = MaterialTheme.typography.labelLarge.copy(color = MaterialTheme.colorScheme.primary))
            Text(title, style = MaterialTheme.typography.titleLarge)
            Text(desc, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
    }
}

// ── For whom ──────────────────────────────────────────────────────────────────

@Composable
private fun ForWhomSection() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f))
            .padding(horizontal = 24.dp, vertical = 64.dp),
        verticalArrangement = Arrangement.spacedBy(32.dp)
    ) {
        SectionLabel("Para quién")
        Text("A quién le duele", style = MaterialTheme.typography.headlineMedium)
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            PersonaCard(
                initials = "PC", role = "Paciente crónico",
                desc = "Vives tu enfermedad todos los días. Continuum la registra para que no tengas que recordar todo en la cita.",
                quote = "\"Quiero cuidarme, pero sacar una cita es una lucha.\"",
                modifier = Modifier.weight(1f)
            )
            PersonaCard(
                initials = "FC", role = "Familiar / cuidador",
                desc = "Dejas de vivir en alerta constante. Ves en tu celular que todo está bien. O que necesitas actuar.",
                quote = "\"Vivimos en estado de alerta constante, sin información clara.\"",
                featured = true, modifier = Modifier.weight(1f)
            )
            PersonaCard(
                initials = "MD", role = "Médico / IPS",
                desc = "Llegas a la consulta sabiendo qué pasó en las últimas semanas. Menos detective, más médico.",
                quote = "\"Detecto la emergencia cuando el daño ya es irreversible.\"",
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
private fun PersonaCard(
    initials: String,
    role: String,
    desc: String,
    quote: String,
    featured: Boolean = false,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        border = if (featured) androidx.compose.foundation.BorderStroke(
            2.dp, MaterialTheme.colorScheme.primary
        ) else null
    ) {
        Column(modifier = Modifier.padding(24.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .background(
                        MaterialTheme.colorScheme.primaryContainer,
                        shape = MaterialTheme.shapes.extraLarge
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    initials, style = MaterialTheme.typography.labelLarge.copy(
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                )
            }
            Text(role, style = MaterialTheme.typography.labelLarge.copy(color = MaterialTheme.colorScheme.primary))
            Text(desc, style = MaterialTheme.typography.bodyMedium)
            Text(
                quote, style = MaterialTheme.typography.bodySmall.copy(
                    fontStyle = FontStyle.Italic,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            )
        }
    }
}

// ── Results ───────────────────────────────────────────────────────────────────

@Composable
private fun ResultsSection() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 64.dp),
        verticalArrangement = Arrangement.spacedBy(32.dp)
    ) {
        SectionLabel("Resultados")
        Text("Datos reales. Hipótesis validada.", style = MaterialTheme.typography.headlineMedium)
        Row(
            horizontalArrangement = Arrangement.spacedBy(2.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            MetricCard("40%", "reducción proyectada en urgencias evitables", Modifier.weight(1f))
            MetricCard("10", "entrevistas con pacientes y médicos. Hipótesis validada.", Modifier.weight(1f))
            MetricCard("15M+", "colombianos con condición crónica. Mercado real.", Modifier.weight(1f))
        }
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            TestimonialCard(
                text = "\"Mi disciplina me mantiene vivo, pero el sistema de salud y mi corazón me mantienen en alerta constante.\"",
                name = "A.R.", role = "Paciente cardíaco · 60 años · Bogotá",
                modifier = Modifier.weight(1f)
            )
            TestimonialCard(
                text = "\"El control médico actual es reactivo; detectamos la emergencia cuando el daño ya es irreversible por falta de seguimiento continuo.\"",
                name = "Dr. C.T.", role = "Médico general · Bogotá · Entrevista validada",
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
private fun MetricCard(number: String, desc: String, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.3f))
    ) {
        Column(
            modifier = Modifier.padding(24.dp).fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(number, style = MaterialTheme.typography.displaySmall.copy(color = MaterialTheme.colorScheme.primary))
            Text(desc, style = MaterialTheme.typography.bodySmall,
                textAlign = androidx.compose.ui.text.style.TextAlign.Center)
        }
    }
}

@Composable
private fun TestimonialCard(text: String, name: String, role: String, modifier: Modifier = Modifier) {
    Card(modifier = modifier) {
        Column(modifier = Modifier.padding(24.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
            Text(text, style = MaterialTheme.typography.bodyLarge.copy(fontStyle = FontStyle.Italic))
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .background(MaterialTheme.colorScheme.primaryContainer, shape = MaterialTheme.shapes.extraLarge),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        name.take(2), style = MaterialTheme.typography.labelMedium.copy(
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    )
                }
                Column {
                    Text(name, style = MaterialTheme.typography.labelLarge)
                    Text(role, style = MaterialTheme.typography.labelSmall.copy(color = MaterialTheme.colorScheme.onSurfaceVariant))
                }
            }
        }
    }
}

// ── Pilot form ────────────────────────────────────────────────────────────────

@Composable
private fun PilotFormSection(
    onSubmit: (name: String, condition: String, city: String, email: String) -> Unit
) {
    var name by remember { mutableStateOf("") }
    var condition by remember { mutableStateOf("") }
    var city by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var submitted by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.2f))
            .padding(horizontal = 24.dp, vertical = 64.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        SectionLabel("Únete al piloto")
        Text("Sé parte del piloto. Cupos limitados.", style = MaterialTheme.typography.headlineMedium)
        Text(
            "Estamos seleccionando los primeros usuarios para el piloto de Continuum en Colombia.",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        if (submitted) {
            Card(colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)) {
                Column(modifier = Modifier.padding(24.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(
                        "¡Listo! Te confirmamos en 24 horas.",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Text("Revisa tu correo para los próximos pasos del piloto.", style = MaterialTheme.typography.bodyMedium)
                }
            }
        } else {
            Card(modifier = Modifier.widthIn(max = 480.dp).fillMaxWidth()) {
                Column(modifier = Modifier.padding(32.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
                    OutlinedTextField(
                        value = name, onValueChange = { name = it },
                        label = { Text("Nombre completo") }, modifier = Modifier.fillMaxWidth()
                    )
                    OutlinedTextField(
                        value = condition, onValueChange = { condition = it },
                        label = { Text("Condición de salud") }, modifier = Modifier.fillMaxWidth()
                    )
                    Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        OutlinedTextField(
                            value = city, onValueChange = { city = it },
                            label = { Text("Ciudad") }, modifier = Modifier.weight(1f)
                        )
                        OutlinedTextField(
                            value = email, onValueChange = { email = it },
                            label = { Text("Correo electrónico") }, modifier = Modifier.weight(1f)
                        )
                    }
                    Button(
                        onClick = {
                            if (name.isNotBlank() && email.isNotBlank()) {
                                onSubmit(name, condition, city, email)
                                submitted = true
                            }
                        },
                        modifier = Modifier.fillMaxWidth().height(52.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                    ) {
                        Text("Quiero ser de los primeros →", style = MaterialTheme.typography.titleMedium)
                    }
                }
            }
        }
    }
}

// ── Shared composable ─────────────────────────────────────────────────────────

@Composable
private fun SectionLabel(text: String) {
    Text(
        text.uppercase(),
        style = MaterialTheme.typography.labelSmall.copy(
            color = MaterialTheme.colorScheme.primary,
            letterSpacing = 2.sp
        )
    )
}
