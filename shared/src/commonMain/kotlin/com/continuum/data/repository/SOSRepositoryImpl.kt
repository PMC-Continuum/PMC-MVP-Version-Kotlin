package com.continuum.data.repository

import com.continuum.data.remote.SupabaseService
import com.continuum.domain.model.Result
import com.continuum.domain.model.SOSEvent
import com.continuum.domain.repository.SOSRepository

class SOSRepositoryImpl(
    private val supabase: SupabaseService
) : SOSRepository {

    override suspend fun triggerSOS(event: SOSEvent): Result<Unit> =
        supabase.insertSOSEvent(event)

    override suspend fun getHistory(userId: String): Result<List<SOSEvent>> =
        // TODO: fetch from Supabase sos_events
        Result.Success(emptyList())
}
