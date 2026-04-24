package com.continuum.domain.repository

import com.continuum.domain.model.Result
import com.continuum.domain.model.UserProfile
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun getProfile(userId: String): Result<UserProfile>
    fun observeCurrentUser(): Flow<UserProfile?>
    suspend fun updateProfile(profile: UserProfile): Result<Unit>
    suspend fun signOut(): Result<Unit>
}
