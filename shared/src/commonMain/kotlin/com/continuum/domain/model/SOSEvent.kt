package com.continuum.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class SOSEvent(
    val id: String,
    val userId: String,
    val clinicalSummary: String,
    val location: GeoPoint? = null,
    val nearestHospital: String? = null,
    val notifiedContacts: List<String> = emptyList(),
    val createdAt: Long
)

@Serializable
data class GeoPoint(val lat: Double, val lng: Double)
