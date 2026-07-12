package com.orka.myfinances.logger

fun interface Logger {
    fun log(tag: String, message: String)
}