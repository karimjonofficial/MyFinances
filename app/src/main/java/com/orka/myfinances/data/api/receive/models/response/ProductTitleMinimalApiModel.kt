package com.orka.myfinances.data.api.receive.models.response

import kotlinx.serialization.Serializable

@Serializable
data class ProductTitleMinimalApiModel(
    val id: Int,
    val name: String
)