package com.orka.myfinances.ui.screens.home.viewmodel.profile

fun interface GetSingle<T> {
    suspend fun getSingle(): T?
}