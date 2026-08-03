package com.orka.myfinances.application.factories

import com.orka.myfinances.format.Formatter
import java.text.DecimalFormat
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.time.Instant

class FormatterImpl(locale: Locale) : Formatter {
    private val dateFormat = SimpleDateFormat("dd.MM.yyyy", locale)
    private val timeFormat = SimpleDateFormat("HH:mm", locale)
    private val decimalFormat = (NumberFormat.getInstance(locale) as DecimalFormat).apply { 
        applyPattern("#,###.##") 
    }

    override fun formatDate(instant: Instant): String {
        return dateFormat.format(Date(instant.toEpochMilliseconds()))
    }

    override fun formatTime(instant: Instant): String {
        return timeFormat.format(Date(instant.toEpochMilliseconds()))
    }

    override fun formatDecimal(value: Double): String {
        return decimalFormat.format(value)
    }

    override fun formatNumber(value: Int): String {
        return decimalFormat.format(value)
    }

    override fun formatDateTime(instant: Instant): String {
        return "${formatDate(instant)} ${formatTime(instant)}"
    }
}
