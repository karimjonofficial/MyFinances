package com.orka.myfinances.lib.extensions

fun Int.format(): String {
    val s = toString()
    val length = s.length

    if (length > 3) {
        val builder = StringBuilder(s)
        val count = (length - 1) / 3

        repeat(count) {
            val index = length - 3 - it * 3
            builder.insert(index, ", ")
        }

        return builder.toString()
    } else return "$this"
}

fun Long.format(): String {
    val s = toString()
    val length = s.length

    if (length > 3) {
        val builder = StringBuilder(s)
        val count = (length - 1) / 3

        repeat(count) {
            val index = length - 3 - it * 3
            builder.insert(index, ", ")
        }

        return builder.toString()
    } else return "$this"
}