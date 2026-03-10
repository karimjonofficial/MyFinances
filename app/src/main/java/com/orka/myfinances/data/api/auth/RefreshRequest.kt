package com.orka.myfinances.data.api.auth

import kotlinx.serialization.Serializable

@Serializable
data class RefreshRequest(val refresh: String)