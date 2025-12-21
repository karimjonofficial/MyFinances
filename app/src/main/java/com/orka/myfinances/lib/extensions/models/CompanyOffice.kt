package com.orka.myfinances.lib.extensions.models

import com.orka.myfinances.data.models.Office
import com.orka.myfinances.data.zipped.CompanyOfficeModel

fun Office.toModel(): CompanyOfficeModel {
    return CompanyOfficeModel(
        id = this.id.value,
        name = this.name,
        companyId = this.company.id.value,
        templates = this.templates,
        address = this.address,
        phone = this.phone
    )
}