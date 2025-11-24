package com.orka.myfinances.lib.data

interface DataSource<T> {
    suspend fun get(): List<T>?
}