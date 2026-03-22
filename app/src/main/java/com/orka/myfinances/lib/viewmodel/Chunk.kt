package com.orka.myfinances.lib.viewmodel

data class Chunk<T>(
    val count: Int,
    val pageIndex: Int,
    val nextPageIndex: Int?,
    val previousPageIndex: Int?,
    val results: List<T>
)