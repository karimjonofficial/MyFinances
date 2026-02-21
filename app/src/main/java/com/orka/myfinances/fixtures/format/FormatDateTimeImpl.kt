package com.orka.myfinances.fixtures.format

import com.orka.myfinances.lib.format.FormatDateTime
import kotlin.time.Instant

class FormatDateTimeImpl : FormatDateTime {
    override fun formatDateTime(instant: Instant): String {
        return "02.12.2026 12:12"
    }
}