package com.orka.myfinances.lib.format

import com.orka.myfinances.data.models.product.ProductTitle
import kotlin.time.Instant

fun interface FormatNames {
    fun formatNames(items: List<ProductTitle>): String
}

fun interface FormatDate {
    fun formatDate(instant: Instant): String
}

fun interface FormatTime {
    fun formatTime(instant: Instant): String
}

fun interface FormatPrice {
    fun formatPrice(price: Double): String
}

fun interface FormatDecimal {
    fun formatDecimal(value: Double): String
}

fun interface FormatDateTime {
    fun formatDateTime(instant: Instant): String
}