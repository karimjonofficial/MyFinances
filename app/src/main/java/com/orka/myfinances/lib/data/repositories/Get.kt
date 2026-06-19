package com.orka.myfinances.lib.data.repositories

fun interface Get<T> {
    suspend fun getAll(search: String?): List<T>?
}