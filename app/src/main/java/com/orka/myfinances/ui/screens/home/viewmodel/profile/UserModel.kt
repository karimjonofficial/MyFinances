package com.orka.myfinances.ui.screens.home.viewmodel.profile

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserModel(
    val id: Int,
    @SerialName("first_name") val firstName: String,
    @SerialName("last_name") val lastName: String?,
    val patronymic: String?,
    val profession: String?,
    @SerialName("username") val userName: String,
    val address: String?,
    val phone: String?
)