package com.continuum.platform

actual class AudioRecorder actual constructor() {
    private var recording = false

    actual fun startRecording() {
        // TODO: Web Audio API / MediaRecorder
        recording = true
    }

    actual fun stopRecording(): ByteArray {
        // TODO: stop MediaRecorder, return recorded bytes
        recording = false
        return ByteArray(0)
    }

    actual suspend fun getTranscription(): String {
        // TODO: Web Speech API
        return ""
    }

    actual fun isRecording(): Boolean = recording

    actual fun release() { recording = false }
}
