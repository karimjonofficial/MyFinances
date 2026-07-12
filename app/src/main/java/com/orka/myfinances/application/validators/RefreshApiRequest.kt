package com.orka.myfinances.application.validators

import kotlinx.serialization.Serializable

@Serializable
data class RefreshApiRequest(
    val refresh: String
)