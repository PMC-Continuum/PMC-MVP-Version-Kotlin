package com.continuum.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class TrendAnalysis(
    val worsening_patterns: List<String> = emptyList(),
    val improving_patterns: List<String> = emptyList(),
    val anomalies: List<String> = emptyList(),
    val recommendation: String = ""
)
