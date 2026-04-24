package com.continuum.presentation.home

import com.continuum.domain.model.ClinicalEntry
import com.continuum.domain.model.UserProfile
import com.continuum.domain.repository.ClinicalRepository
import com.continuum.domain.repository.UserRepository
import com.continuum.domain.model.onError
import com.continuum.domain.model.onSuccess
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

data class HomeState(
    val profile: UserProfile? = null,
    val recentEntries: List<ClinicalEntry> = emptyList(),
    val isLoading: Boolean = true,
    val error: String? = null
)

class HomeViewModel(
    private val userRepo: UserRepository,
    private val clinicalRepo: ClinicalRepository,
    private val userId: String,
    private val scope: CoroutineScope = CoroutineScope(Dispatchers.Main)
) {
    private val _state = MutableStateFlow(HomeState())
    val state: StateFlow<HomeState> = _state.asStateFlow()

    init { load() }

    fun load() {
        scope.launch {
            _state.update { it.copy(isLoading = true, error = null) }

            userRepo.getProfile(userId)
                .onSuccess { profile -> _state.update { it.copy(profile = profile) } }
                .onError   { err    -> _state.update { it.copy(error = err) } }

            clinicalRepo.getEntries(userId)
                .onSuccess { entries ->
                    _state.update { it.copy(recentEntries = entries.take(5), isLoading = false) }
                }
                .onError { err ->
                    _state.update { it.copy(error = err, isLoading = false) }
                }
        }
    }
}
