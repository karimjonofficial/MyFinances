package com.orka.myfinances.fixtures.format

import com.orka.myfinances.lib.format.FormatDate
import kotlin.time.Instant

class FormatDateImpl : FormatDate {
    override fun formatDate(instant: Instant): String {
        return "02.12.2026"
    }
}