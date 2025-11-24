package com.orka.myfinances.lib.data

interface Repository<T> {
    suspend fun get(): List<T>?
}