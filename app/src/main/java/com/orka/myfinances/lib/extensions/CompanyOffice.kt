package com.orka.myfinances.lib.extensions

import com.orka.myfinances.data.models.CompanyOffice
import com.orka.myfinances.data.zipped.CompanyOfficeModel

fun CompanyOffice.toModel(): CompanyOfficeModel {
    return CompanyOfficeModel(
        id = this.id.value,
        name = this.name,
        companyId = this.company.id.value,
        templates = this.templates,
        address = this.address,
        phone = this.phone
    )
}