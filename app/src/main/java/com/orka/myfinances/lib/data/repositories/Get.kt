package com.orka.myfinances.lib.data.repositories

interface Get<T> {
    suspend fun get(): List<T>?
}