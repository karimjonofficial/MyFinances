package com.orka.myfinances.lib.logger

fun interface Logger {
    fun log(tag: String, message: String)
}