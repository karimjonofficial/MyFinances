package com.orka.myfinances.application.factories

import com.orka.myfinances.data.models.product.ProductTitle
import com.orka.myfinances.lib.format.FormatDate
import com.orka.myfinances.lib.format.FormatDateTime
import com.orka.myfinances.lib.format.FormatDecimal
import com.orka.myfinances.lib.format.FormatLocalDate
import com.orka.myfinances.lib.format.FormatNames
import com.orka.myfinances.lib.format.FormatPrice
import com.orka.myfinances.lib.format.FormatTime
import kotlinx.datetime.LocalDate
import kotlinx.datetime.number
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import kotlin.time.Instant

class Formatter : FormatNames, FormatDate, FormatTime, FormatPrice, FormatDecimal, FormatDateTime, FormatLocalDate {
    private val dateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
    private val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
    private val priceFormat = DecimalFormat("#,###.##")
    private val decimalFormat = DecimalFormat("#,###.##")

    override fun formatNames(items: List<ProductTitle>): String {
        return items.joinToString { it.name }
    }

    override fun formatDate(instant: Instant): String {
        return dateFormat.format(Date(instant.toEpochMilliseconds()))
    }

    override fun formatTime(instant: Instant): String {
        return timeFormat.format(Date(instant.toEpochMilliseconds()))
    }

    override fun formatPrice(price: Double): String {
        return priceFormat.format(price) + " UZS"
    }

    override fun formatDecimal(value: Double): String {
        return decimalFormat.format(value)
    }

    override fun formatDateTime(instant: Instant): String {
        return "${formatDate(instant)} ${formatTime(instant)}"
    }

    override fun formatLocalDate(date: LocalDate): String {
        val calendar = Calendar.getInstance()
        calendar.set(date.year, date.month.number - 1, date.day)
        return dateFormat.format(calendar.time)
    }
}