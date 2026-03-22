package com.orka.myfinances.lib.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ChunkApiModel<T>(
    val count: Int,
    @SerialName("current_page") val pageIndex: Int,
    @SerialName("next_page") val nextPageIndex: Int?,
    @SerialName("previous_page") val previousPageIndex: Int?,
    val results: List<T>
)