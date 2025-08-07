package com.orka.myfinances.data.zipped

data class CompanyModel(
    val id: Int,
    val name: String,
    val address: String? = null,
    val phone: String? = null
)