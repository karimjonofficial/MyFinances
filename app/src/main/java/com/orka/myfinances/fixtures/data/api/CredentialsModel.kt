package com.orka.myfinances.fixtures.data.api

import kotlinx.serialization.Serializable

@Serializable
data class CredentialsModel(
    val access: String,
    val refresh: String
)