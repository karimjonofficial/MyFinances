package com.orka.myfinances.data.dtos.branch

data class BranchDto(
    val id: Int,
    val companyId: Int,
    val name: String,
    val address: String,
    val phone: String,
)
