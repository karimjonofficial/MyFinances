package com.orka.myfinances.application.manager

import com.orka.myfinances.data.models.product.ProductTitle
import com.orka.myfinances.lib.format.Formatter
import kotlinx.datetime.LocalDate
import kotlin.time.Instant

class DummyFormatter : Formatter {
    override fun formatNames(items: List<ProductTitle>) = ""
    override fun formatDate(instant: Instant) = ""
    override fun formatTime(instant: Instant) = ""
    override fun formatPrice(price: Double) = ""
    override fun formatDecimal(value: Double) = ""
    override fun formatDateTime(instant: Instant) = ""
    override fun formatLocalDate(date: LocalDate) = ""
}
