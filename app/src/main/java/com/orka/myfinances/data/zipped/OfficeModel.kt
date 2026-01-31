package com.orka.myfinances.data.zipped

data class OfficeModel(
    val id: Int,
    val name: String,
    val companyId: Int,
    val address: String? = null,
    val phone: String? = null
)