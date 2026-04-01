package com.orka.myfinances.data.api.auth.models.request

import kotlinx.serialization.Serializable

@Serializable
data class RefreshRequest(val refresh: String)