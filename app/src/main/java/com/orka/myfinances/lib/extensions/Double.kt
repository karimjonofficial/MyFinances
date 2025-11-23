package com.orka.myfinances.lib.extensions

import kotlin.math.log10

fun Double.format(): String {
    val int = toLong()

    if (int.toDouble() == this) {
        return int.format()
    } else {
        val dotIndex = log10(this).toInt() + 1
        val s = toString()
        val r = s.substring(dotIndex)
        return int.format() + r
    }
}

fun String.parseFromFormatted(): Double? {
    val s = replace(", ", "")
    return s.toDoubleOrNull()
}