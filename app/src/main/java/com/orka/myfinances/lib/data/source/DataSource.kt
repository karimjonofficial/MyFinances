package com.orka.myfinances.lib.data.source

interface DataSource<T> {
    suspend fun get(): List<T>?
}