package com.orka.myfinances.fixtures.format

import com.orka.myfinances.format.Formatter
import kotlin.time.Instant

class FormatterFixture : Formatter {
    override fun formatDate(instant: Instant): String = "12.01.2024"
    override fun formatTime(instant: Instant): String = "12:00"
    override fun formatDecimal(value: Double): String = "1,000.00"
    override fun formatNumber(value: Int): String = "1,000"
    override fun formatDateTime(instant: Instant): String = "12.01.2024 12:00"
}
