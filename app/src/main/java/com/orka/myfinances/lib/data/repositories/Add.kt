package com.orka.myfinances.lib.data.repositories

fun interface Add<Response, Request> {
    suspend fun add(request: Request): Response?
}