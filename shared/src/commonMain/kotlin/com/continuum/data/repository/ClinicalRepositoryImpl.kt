package com.continuum.data.repository

import com.continuum.data.remote.SupabaseService
import com.continuum.domain.model.ClinicalEntry
import com.continuum.domain.model.Result
import com.continuum.domain.repository.ClinicalRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ClinicalRepositoryImpl(
    private val supabase: SupabaseService
) : ClinicalRepository {

    // In-memory cache for offline support
    // Replace with SQLDelight in production
    private val cache = mutableListOf<ClinicalEntry>()

    override suspend fun submitEntry(entry: ClinicalEntry): Result<Unit> {
        cache.add(0, entry)
        return supabase.insertClinicalEntry(entry)
    }

    override fun observeEntries(userId: String): Flow<List<ClinicalEntry>> = flow {
        emit(cache.filter { it.userId == userId })
        val remote = supabase.getClinicalEntries(userId)
        if (remote is Result.Success) {
            cache.clear()
            cache.addAll(remote.data)
            emit(cache)
        }
    }

    override suspend fun getEntries(userId: String): Result<List<ClinicalEntry>> {
        if (cache.isNotEmpty()) return Result.Success(cache.filter { it.userId == userId })
        return supabase.getClinicalEntries(userId).also { result ->
            if (result is Result.Success) {
                cache.clear()
                cache.addAll(result.data)
            }
        }
    }

    override suspend fun syncPending(): Result<Int> {
        // TODO: implement SQLDelight unsynced entries → Supabase
        return Result.Success(0)
    }
}
