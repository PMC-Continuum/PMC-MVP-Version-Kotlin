package com.continuum.agent

import com.continuum.data.remote.ClaudeService
import com.continuum.domain.model.ClinicalEntry
import com.continuum.domain.model.Result
import com.continuum.domain.model.TrendAnalysis
import kotlinx.serialization.json.Json

class TrendAnalysisAgent(private val claude: ClaudeService) {

    private val json = Json { ignoreUnknownKeys = true }

    private val system = """
        You are a clinical trend analyst for a chronic disease monitoring platform in Colombia.
        Given the last 30 days of a patient's daily health entries, identify:
        - worsening_patterns: symptoms or metrics getting worse
        - improving_patterns: symptoms or metrics improving
        - anomalies: unusual single events worth flagging
        - recommendation: one action the patient should discuss with their doctor (in Spanish)

        Respond ONLY with valid JSON — no text outside the JSON:
        {"worsening_patterns":[],"improving_patterns":[],"anomalies":[],"recommendation":""}
    """.trimIndent()

    suspend fun analyze(entries: List<ClinicalEntry>): Result<TrendAnalysis> {
        if (entries.isEmpty()) return Result.Error("No entries to analyze")
        return try {
            val summary = entries.takeLast(30).joinToString("\n") { e ->
                "date=${e.recordedAt} symptoms=${e.symptoms} intensity=${e.intensityScore}"
            }
            val raw = claude.complete(system, summary)
            Result.Success(json.decodeFromString(raw))
        } catch (e: Exception) {
            Result.Error("TrendAnalysisAgent failed: ${e.message}", e)
        }
    }
}
