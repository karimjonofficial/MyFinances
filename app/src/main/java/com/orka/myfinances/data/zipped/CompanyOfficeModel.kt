package com.orka.myfinances.data.zipped

import com.orka.myfinances.data.models.template.Template

data class CompanyOfficeModel(
    val id: Int,
    val name: String,
    val companyId: Int,
    val templates: List<Template>,
    val address: String? = null,
    val phone: String? = null
)