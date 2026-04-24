package com.continuum.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class HealthTrend(
    val id: String,
    val userId: String,
    val metric: String,
    val value: Double,
    val unit: String? = null,
    val source: TrendSource,
    val recordedAt: Long
)

enum class TrendSource { MANUAL, WEARABLE, AI_INFERRED }
