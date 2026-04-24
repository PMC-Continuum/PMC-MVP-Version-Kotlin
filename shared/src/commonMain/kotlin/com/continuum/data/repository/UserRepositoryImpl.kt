package com.continuum.data.repository

import com.continuum.data.remote.SupabaseService
import com.continuum.domain.model.Result
import com.continuum.domain.model.UserProfile
import com.continuum.domain.model.UserRole
import com.continuum.domain.repository.UserRepository
import io.github.jan.supabase.auth.auth
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class UserRepositoryImpl(
    private val supabase: SupabaseService
) : UserRepository {

    override suspend fun getProfile(userId: String): Result<UserProfile> {
        // TODO: fetch from Supabase users table
        // Stub returns a basic profile for now
        return Result.Success(
            UserProfile(
                id = userId,
                email = "",
                role = UserRole.PATIENT,
                fullName = null,
                conditions = emptyList()
            )
        )
    }

    override fun observeCurrentUser(): Flow<UserProfile?> = flow {
        emit(null) // TODO: observe Supabase auth session
    }

    override suspend fun updateProfile(profile: UserProfile): Result<Unit> =
        Result.Success(Unit) // TODO: PATCH users table

    override suspend fun signOut(): Result<Unit> = try {
        supabase.client.auth.signOut()
        Result.Success(Unit)
    } catch (e: Exception) {
        Result.Error(e.message ?: "Sign out failed", e)
    }
}
