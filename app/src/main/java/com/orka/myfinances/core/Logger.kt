package com.orka.myfinances.core

fun interface Logger {
    fun log(tag: String, message: String)
}