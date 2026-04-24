package com.continuum.platform

import kotlin.js.Date
import kotlin.random.Random

actual fun getCurrentEpochMillis(): Long = Date().getTime().toLong()

actual fun generateUuid(): String {
    val hex = "0123456789abcdef"
    return (1..36).map { i ->
        when (i) {
            9, 14, 19, 24 -> '-'
            15 -> '4'
            20 -> hex[(Random.nextInt(4) or 8)]
            else -> hex[Random.nextInt(16)]
        }
    }.joinToString("")
}
