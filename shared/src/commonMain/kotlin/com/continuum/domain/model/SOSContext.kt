package com.continuum.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class SOSContext(
    val conditions: List<String> = emptyList(),
    val recent_symptoms: List<String> = emptyList(),
    val urgency: String = "medium",
    val summary: String = ""
)
