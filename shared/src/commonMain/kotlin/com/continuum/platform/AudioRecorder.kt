package com.continuum.platform

expect class AudioRecorder() {
    fun startRecording()
    fun stopRecording(): ByteArray
    suspend fun getTranscription(): String
    fun isRecording(): Boolean
    fun release()
}
