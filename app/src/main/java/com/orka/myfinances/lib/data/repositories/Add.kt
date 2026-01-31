package com.orka.myfinances.lib.data.repositories

fun interface Add<T, R> {
    suspend fun add(request: R): T?
}