package com.orka.myfinances.lib.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Item(
    @SerialName("product") val id: Int,
    val amount: Int
)