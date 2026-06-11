package com.orka.myfinances.lib.ui.models

data class ChunkUiModel<T>(
    val count: Int,
    val pageIndex: Int,
    val nextPageIndex: Int?,
    val previousPageIndex: Int?,
    val content: Map<String, List<T>>
)