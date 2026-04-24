package com.continuum.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class ClinicalEntry(
    val id: String,
    val userId: String,
    val rawVoice: String? = null,
    val structuredData: Map<String, String> = emptyMap(),
    val symptoms: List<String> = emptyList(),
    val intensityScore: Int? = null,
    val aiSummary: String? = null,
    val recordedAt: Long
)
