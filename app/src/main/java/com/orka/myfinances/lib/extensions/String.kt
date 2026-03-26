package com.orka.myfinances.lib.extensions

fun String.valueOrNull(): String? {
    return if(isNullOrBlank()) null else this
}

fun String.stickyHeaderKey(): String {
    val firstLetter = trim().firstOrNull()?.uppercaseChar()
    return if (firstLetter != null && firstLetter.isLetter()) firstLetter.toString() else "#"
}