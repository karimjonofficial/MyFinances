package com.orka.myfinances.lib.extensions

fun String.valueOrNull(): String? {
    return if(isNullOrBlank()) null else this
}