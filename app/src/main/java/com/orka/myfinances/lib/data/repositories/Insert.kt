package com.orka.myfinances.lib.data.repositories

fun interface Insert<R> {
    suspend fun insert(request: R): Boolean
}