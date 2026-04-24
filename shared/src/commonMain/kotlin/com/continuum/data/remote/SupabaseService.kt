package com.continuum.data.remote

import com.continuum.config.AppConfig
import com.continuum.domain.model.*
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.postgrest.postgrest
import io.github.jan.supabase.realtime.Realtime
import kotlinx.serialization.Serializable

class SupabaseService {

    val client: SupabaseClient = createSupabaseClient(
        supabaseUrl = AppConfig.SUPABASE_URL,
        supabaseKey = AppConfig.SUPABASE_ANON_KEY
    ) {
        install(Postgrest)
        install(Auth)
        install(Realtime)
    }

    // ── DTOs ────────────────────────────────────────────────────────────────

    @Serializable
    private data class ClinicalEntryDto(
        val id: String? = null,
        val user_id: String,
        val raw_voice: String? = null,
        val structured_data: String = "{}",
        val symptoms: List<String> = emptyList(),
        val intensity_score: Int? = null,
        val ai_summary: String? = null
    )

    @Serializable
    private data class SOSEventDto(
        val id: String? = null,
        val user_id: String,
        val clinical_summary: String,
        val nearest_hospital: String? = null
    )

    @Serializable
    private data class WaitlistDto(
        val email: String,
        val full_name: String? = null,
        val condition: String? = null,
        val city: String? = null
    )

    // ── Clinical entries ─────────────────────────────────────────────────────

    suspend fun insertClinicalEntry(entry: ClinicalEntry): Result<Unit> =
        runCatching {
            client.postgrest["clinical_entries"].insert(
                ClinicalEntryDto(
                    id = entry.id,
                    user_id = entry.userId,
                    raw_voice = entry.rawVoice,
                    structured_data = entry.structuredData.toString(),
                    symptoms = entry.symptoms,
                    intensity_score = entry.intensityScore,
                    ai_summary = entry.aiSummary
                )
            )
        }.fold(
            onSuccess = { Result.Success(Unit) },
            onFailure = { Result.Error(it.message ?: "Insert failed", it) }
        )

    suspend fun getClinicalEntries(userId: String): Result<List<ClinicalEntry>> =
        runCatching {
            client.postgrest["clinical_entries"]
                .select { filter { eq("user_id", userId) } }
                .decodeList<ClinicalEntryDto>()
                .map { dto ->
                    ClinicalEntry(
                        id = dto.id ?: "",
                        userId = dto.user_id,
                        rawVoice = dto.raw_voice,
                        symptoms = dto.symptoms,
                        intensityScore = dto.intensity_score,
                        aiSummary = dto.ai_summary,
                        recordedAt = 0L
                    )
                }
        }.fold(
            onSuccess = { Result.Success(it) },
            onFailure = { Result.Error(it.message ?: "Fetch failed", it) }
        )

    // ── SOS events ───────────────────────────────────────────────────────────

    suspend fun insertSOSEvent(event: SOSEvent): Result<Unit> =
        runCatching {
            client.postgrest["sos_events"].insert(
                SOSEventDto(
                    id = event.id,
                    user_id = event.userId,
                    clinical_summary = event.clinicalSummary,
                    nearest_hospital = event.nearestHospital
                )
            )
        }.fold(
            onSuccess = { Result.Success(Unit) },
            onFailure = { Result.Error(it.message ?: "SOS insert failed", it) }
        )

    // ── Waitlist ──────────────────────────────────────────────────────────────

    suspend fun addToWaitlist(
        email: String,
        fullName: String?,
        condition: String?,
        city: String?
    ): Result<Unit> =
        runCatching {
            client.postgrest["waitlist"].insert(
                WaitlistDto(email, fullName, condition, city)
            )
        }.fold(
            onSuccess = { Result.Success(Unit) },
            onFailure = { Result.Error(it.message ?: "Waitlist error", it) }
        )
}
