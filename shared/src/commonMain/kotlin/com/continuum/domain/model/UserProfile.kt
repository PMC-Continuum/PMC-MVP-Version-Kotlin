package com.continuum.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class UserProfile(
    val id: String,
    val email: String,
    val role: UserRole,
    val fullName: String? = null,
    val conditions: List<String> = emptyList(),
    val sosContacts: List<SosContact> = emptyList()
)

@Serializable
data class SosContact(
    val name: String,
    val phone: String,
    val email: String? = null
)

enum class UserRole { PATIENT, CAREGIVER, DOCTOR }
