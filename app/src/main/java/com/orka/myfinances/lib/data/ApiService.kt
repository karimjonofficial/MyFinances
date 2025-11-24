package com.orka.myfinances.lib.data

interface ApiService<T> {
    suspend fun get(): List<T>?
}