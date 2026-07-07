package com.orka.myfinances.data.api.auth.models.response

import kotlinx.serialization.Serializable

@Serializable
data class CredentialsApiModel(
    val access: String,
    val refresh: String
)