package com.orka.myfinances.data.dtos.office

data class OfficeDto(
    val id: Int,
    val companyId: Int,
    val name: String,
    val address: String,
    val phone: String,
)
