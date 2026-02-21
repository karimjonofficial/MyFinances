package com.orka.myfinances.fixtures.format

import com.orka.myfinances.lib.format.FormatTime
import kotlin.time.Instant

class FormatTimeImpl : FormatTime {
    override fun formatTime(instant: Instant): String {
        return "12:12"
    }
}