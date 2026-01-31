package com.orka.myfinances.lib.extensions.models

import com.orka.myfinances.data.models.Office
import com.orka.myfinances.data.zipped.OfficeModel

fun Office.toModel(): OfficeModel {
    return OfficeModel(
        id = this.id.value,
        name = this.name,
        companyId = this.company.id.value,
        address = this.address,
        phone = this.phone
    )
}