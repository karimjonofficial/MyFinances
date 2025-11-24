package com.orka.myfinances.lib.data

interface Storage<T> {
    suspend fun get(): List<T>?
}