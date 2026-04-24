package com.continuum.platform

actual class AudioRecorder actual constructor() {
    private var recording = false

    actual fun startRecording() {
        // TODO: configure MediaRecorder with AMR_NB, start()
        recording = true
    }

    actual fun stopRecording(): ByteArray {
        // TODO: stop MediaRecorder, read output file bytes
        recording = false
        return ByteArray(0)
    }

    actual suspend fun getTranscription(): String {
        // TODO: Android SpeechRecognizer or Whisper API call
        return ""
    }

    actual fun isRecording(): Boolean = recording

    actual fun release() { recording = false }
}
