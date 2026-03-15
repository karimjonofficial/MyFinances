package com.orka.myfinances.data.api.receive

import kotlinx.serialization.Serializable

@Serializable
data class ProductTitleMinimalApiModel(
    val id: Int,
    val name: String
)