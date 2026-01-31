package com.orka.myfinances.lib.data.repositories

fun interface GetByParameter<T, P> {
    suspend fun get(parameter: P): List<T>?
}