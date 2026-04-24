package com.continuum.platform

import platform.Foundation.NSDate
import platform.Foundation.NSUUID
import platform.Foundation.timeIntervalSince1970

actual fun getCurrentEpochMillis(): Long =
    (NSDate().timeIntervalSince1970 * 1000).toLong()

actual fun generateUuid(): String = NSUUID().UUIDString()
