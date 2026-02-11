package com.orka.myfinances.lib.data.repositories

fun interface Generator<T> {
    fun generate(): T
}