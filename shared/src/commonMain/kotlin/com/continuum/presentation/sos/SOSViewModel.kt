package com.continuum.presentation.sos

import com.continuum.domain.model.GeoPoint
import com.continuum.domain.model.SOSEvent
import com.continuum.domain.model.onError
import com.continuum.domain.model.onSuccess
import com.continuum.domain.usecase.TriggerSOSUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

sealed class SOSState {
    object Idle      : SOSState()
    object Countdown : SOSState()
    object Sending   : SOSState()
    data class Sent(val event: SOSEvent)   : SOSState()
    data class Error(val message: String) : SOSState()
}

class SOSViewModel(
    private val useCase: TriggerSOSUseCase,
    private val userId: String,
    private val scope: CoroutineScope = CoroutineScope(Dispatchers.Main)
) {
    private val _state = MutableStateFlow<SOSState>(SOSState.Idle)
    val state: StateFlow<SOSState> = _state.asStateFlow()

    fun initiateSOS()  { _state.value = SOSState.Countdown }
    fun cancel()       { _state.value = SOSState.Idle }
    fun reset()        { _state.value = SOSState.Idle }

    fun confirmSOS(location: GeoPoint? = null) {
        scope.launch {
            _state.value = SOSState.Sending
            useCase(userId, location)
                .onSuccess { event -> _state.value = SOSState.Sent(event) }
                .onError   { err   -> _state.value = SOSState.Error(err) }
        }
    }
}
