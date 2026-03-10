package com.orka.myfinances.data.api.user

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserApiModel(
    val id: Int,
    @SerialName("first_name") val firstName: String,
    @SerialName("last_name") val lastName: String?,
    val patronymic: String? = null,
    val profession: String? = null,
    @SerialName("username") val userName: String,
    val address: String? = null,
    val phone: String? = null
)