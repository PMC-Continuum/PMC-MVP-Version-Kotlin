package com.continuum.platform

actual class AudioRecorder actual constructor() {
    private var recording = false

    actual fun startRecording() {
        // TODO: AVAudioRecorder with kAudioFormatMPEG4AAC
        recording = true
    }

    actual fun stopRecording(): ByteArray {
        // TODO: stop AVAudioRecorder, read file bytes
        recording = false
        return ByteArray(0)
    }

    actual suspend fun getTranscription(): String {
        // TODO: SFSpeechRecognizer
        return ""
    }

    actual fun isRecording(): Boolean = recording

    actual fun release() { recording = false }
}
