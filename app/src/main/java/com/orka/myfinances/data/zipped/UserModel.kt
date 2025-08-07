package com.orka.myfinances.data.zipped

data class UserModel(
    val id: Int,
    val firstName: String,
    val userName: String,
    val companyId: Int,
    val lastName: String? = null,
    val patronymic: String? = null,
    val phone: String? = null,
    val address: String? = null
)