package com.continuum.presentation.voiceentry

import com.continuum.domain.model.ClinicalEntry
import com.continuum.domain.model.onError
import com.continuum.domain.model.onSuccess
import com.continuum.domain.usecase.SubmitVoiceEntryUseCase
import com.continuum.platform.AudioRecorder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

sealed class VoiceEntryState {
    object Idle       : VoiceEntryState()
    object Recording  : VoiceEntryState()
    object Processing : VoiceEntryState()
    data class Review(val entry: ClinicalEntry) : VoiceEntryState()
    data class Saved(val entry: ClinicalEntry)  : VoiceEntryState()
    data class Error(val message: String)       : VoiceEntryState()
}

class VoiceEntryViewModel(
    private val useCase: SubmitVoiceEntryUseCase,
    private val recorder: AudioRecorder,
    private val userId: String,
    private val scope: CoroutineScope = CoroutineScope(Dispatchers.Main)
) {
    private val _state = MutableStateFlow<VoiceEntryState>(VoiceEntryState.Idle)
    val state: StateFlow<VoiceEntryState> = _state.asStateFlow()

    fun startRecording() {
        recorder.startRecording()
        _state.value = VoiceEntryState.Recording
    }

    fun stopAndProcess() {
        scope.launch {
            _state.value = VoiceEntryState.Processing
            recorder.stopRecording()
            val transcription = recorder.getTranscription()
            useCase(userId, transcription)
                .onSuccess { entry -> _state.value = VoiceEntryState.Review(entry) }
                .onError   { err   -> _state.value = VoiceEntryState.Error(err) }
        }
    }

    fun save() {
        val current = _state.value
        if (current is VoiceEntryState.Review) {
            _state.value = VoiceEntryState.Saved(current.entry)
        }
    }

    fun reset() { _state.value = VoiceEntryState.Idle }
}
