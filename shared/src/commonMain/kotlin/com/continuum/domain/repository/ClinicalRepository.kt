package com.continuum.domain.repository

import com.continuum.domain.model.ClinicalEntry
import com.continuum.domain.model.Result
import kotlinx.coroutines.flow.Flow

interface ClinicalRepository {
    suspend fun submitEntry(entry: ClinicalEntry): Result<Unit>
    fun observeEntries(userId: String): Flow<List<ClinicalEntry>>
    suspend fun getEntries(userId: String): Result<List<ClinicalEntry>>
    suspend fun syncPending(): Result<Int>
}
