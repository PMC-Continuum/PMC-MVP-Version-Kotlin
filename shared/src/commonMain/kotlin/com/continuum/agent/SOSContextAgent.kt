package com.continuum.agent

import com.continuum.data.remote.ClaudeService
import com.continuum.domain.model.ClinicalEntry
import com.continuum.domain.model.Result
import com.continuum.domain.model.SOSContext
import com.continuum.domain.model.UserProfile
import kotlinx.serialization.json.Json

class SOSContextAgent(private val claude: ClaudeService) {

    private val json = Json { ignoreUnknownKeys = true }

    private val system = """
        You are an emergency clinical summarizer for a health monitoring app in Colombia.
        Given a patient's recent health entries and profile, generate a concise clinical
        summary for emergency responders. Include: active conditions, recent symptoms,
        and urgency level (low / medium / high).

        The summary field must be in Spanish.
        Respond ONLY with valid JSON — no text outside the JSON:
        {"conditions":[],"recent_symptoms":[],"urgency":"medium","summary":""}
    """.trimIndent()

    suspend fun buildContext(
        profile: UserProfile,
        recentEntries: List<ClinicalEntry>
    ): Result<SOSContext> = try {
        val input = buildString {
            appendLine("Patient: ${profile.fullName}, conditions: ${profile.conditions}")
            appendLine("Last 7 entries:")
            recentEntries.takeLast(7).forEach { e ->
                appendLine("- symptoms: ${e.symptoms}, intensity: ${e.intensityScore}")
            }
        }
        val raw = claude.complete(system, input)
        Result.Success(json.decodeFromString(raw))
    } catch (e: Exception) {
        Result.Error("SOSContextAgent failed: ${e.message}", e)
    }
}
