package com.continuum.agent

import com.continuum.data.remote.ClaudeService
import com.continuum.domain.model.ClinicalEntry
import com.continuum.domain.model.Result
import com.continuum.platform.generateUuid
import com.continuum.platform.getCurrentEpochMillis
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

@Serializable
private data class SieveResponse(
    val symptoms: List<String> = emptyList(),
    val intensity_score: Int = 0,
    val structured_data: Map<String, String> = emptyMap(),
    val ai_summary: String = ""
)

class ClinicalSieveAgent(private val claude: ClaudeService) {

    private val json = Json { ignoreUnknownKeys = true }

    private val system = """
        You are a clinical data extractor for a chronic disease monitoring app in Colombia.
        Given a patient's voice note transcription in Spanish, extract:
        - symptoms: list of symptoms mentioned (in Spanish)
        - intensity_score: overall severity 1-10
        - structured_data: key clinical facts as a flat string map
        - ai_summary: one-sentence doctor-facing summary in Spanish

        Respond ONLY with valid JSON matching exactly this schema — no text outside the JSON:
        {"symptoms":[],"intensity_score":0,"structured_data":{},"ai_summary":""}
    """.trimIndent()

    suspend fun process(userId: String, rawVoice: String): Result<ClinicalEntry> = try {
        val raw = claude.complete(system, rawVoice)
        val parsed = json.decodeFromString<SieveResponse>(raw)
        Result.Success(
            ClinicalEntry(
                id = generateUuid(),
                userId = userId,
                rawVoice = rawVoice,
                structuredData = parsed.structured_data,
                symptoms = parsed.symptoms,
                intensityScore = parsed.intensity_score.coerceIn(1, 10),
                aiSummary = parsed.ai_summary,
                recordedAt = getCurrentEpochMillis()
            )
        )
    } catch (e: Exception) {
        Result.Error("ClinicalSieveAgent failed: ${e.message}", e)
    }
}
