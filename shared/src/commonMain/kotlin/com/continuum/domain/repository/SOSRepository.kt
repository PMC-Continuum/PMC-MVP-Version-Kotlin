package com.continuum.domain.repository

import com.continuum.domain.model.Result
import com.continuum.domain.model.SOSEvent

interface SOSRepository {
    suspend fun triggerSOS(event: SOSEvent): Result<Unit>
    suspend fun getHistory(userId: String): Result<List<SOSEvent>>
}
