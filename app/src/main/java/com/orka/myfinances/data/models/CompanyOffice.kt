package com.orka.myfinances.data.models

import com.orka.myfinances.data.models.template.Template

data class CompanyOffice(
    val id: Id,
    val name: String,
    val company: Company,
    val templates: List<Template>,
    val address: String? = null,
    val phone: String? = null
)