package com.orka.myfinances.data.repositories

data class Chunk<T>(
    val count: Int,
    val pageIndex: Int,
    val nextPageIndex: Int?,
    val previousPageIndex: Int?,
    val results: List<T>
)