package com.continuum.domain.usecase

import com.continuum.agent.SOSContextAgent
import com.continuum.domain.model.GeoPoint
import com.continuum.domain.model.Result
import com.continuum.domain.model.SOSEvent
import com.continuum.domain.repository.ClinicalRepository
import com.continuum.domain.repository.SOSRepository
import com.continuum.domain.repository.UserRepository
import com.continuum.platform.generateUuid
import com.continuum.platform.getCurrentEpochMillis

class TriggerSOSUseCase(
    private val sosRepo: SOSRepository,
    private val clinicalRepo: ClinicalRepository,
    private val userRepo: UserRepository,
    private val agent: SOSContextAgent
) {
    suspend operator fun invoke(
        userId: String,
        location: GeoPoint? = null
    ): Result<SOSEvent> {
        val profileResult = userRepo.getProfile(userId)
        if (profileResult is Result.Error) return profileResult

        val entriesResult = clinicalRepo.getEntries(userId)
        if (entriesResult is Result.Error) return entriesResult

        val profile = (profileResult as Result.Success).data
        val entries = (entriesResult as Result.Success).data

        val contextResult = agent.buildContext(profile, entries)
        if (contextResult is Result.Error) return contextResult

        val context = (contextResult as Result.Success).data

        val event = SOSEvent(
            id = generateUuid(),
            userId = userId,
            clinicalSummary = context.summary,
            location = location,
            createdAt = getCurrentEpochMillis()
        )

        return sosRepo.triggerSOS(event).let { saveResult ->
            if (saveResult is Result.Error) saveResult
            else Result.Success(event)
        }
    }
}
