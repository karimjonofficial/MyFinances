package com.orka.myfinances.data.models

data class CompanyOffice(
    val id: Id,
    val name: String,
    val company: Company,
    val templates: List<ProductTemplate>,
    val address: String? = null,
    val phone: String? = null
)
