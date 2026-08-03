package com.orka.myfinances.format

import kotlin.time.Instant

interface Formatter {
    fun formatDate(instant: Instant): String
    fun formatTime(instant: Instant): String
    fun formatDecimal(value: Double): String
    fun formatNumber(value: Int): String
    fun formatDateTime(instant: Instant): String
}
