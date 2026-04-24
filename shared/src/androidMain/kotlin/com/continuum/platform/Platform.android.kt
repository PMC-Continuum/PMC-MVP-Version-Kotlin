package com.continuum.platform

import java.util.UUID

actual fun getCurrentEpochMillis(): Long = System.currentTimeMillis()
actual fun generateUuid(): String = UUID.randomUUID().toString()
