package com.orka.myfinances.data.api.auth

import kotlinx.serialization.Serializable

@Serializable
data class CredentialsModel(
    val access: String,
    val refresh: String
)