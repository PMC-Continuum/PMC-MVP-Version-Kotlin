package com.continuum.presentation.dashboard

import com.continuum.domain.model.ClinicalEntry
import com.continuum.domain.model.TrendAnalysis
import com.continuum.domain.model.onError
import com.continuum.domain.model.onSuccess
import com.continuum.domain.usecase.GetHealthTrendsUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

data class DashboardState(
    val entries: List<ClinicalEntry> = emptyList(),
    val analysis: TrendAnalysis? = null,
    val isLoading: Boolean = true,
    val error: String? = null
)

class DashboardViewModel(
    private val useCase: GetHealthTrendsUseCase,
    private val userId: String,
    private val scope: CoroutineScope = CoroutineScope(Dispatchers.Main)
) {
    private val _state = MutableStateFlow(DashboardState())
    val state: StateFlow<DashboardState> = _state.asStateFlow()

    init { load() }

    fun load() {
        scope.launch {
            _state.update { it.copy(isLoading = true, error = null) }
            useCase(userId)
                .onSuccess { result ->
                    _state.update {
                        it.copy(
                            entries = result.entries,
                            analysis = result.analysis,
                            isLoading = false
                        )
                    }
                }
                .onError { err ->
                    _state.update { it.copy(error = err, isLoading = false) }
                }
        }
    }
}
