package com.orka.myfinances.lib.data

interface Mapper<T, R> {
    fun map(model: R): T
}