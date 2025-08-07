package com.orka.myfinances.lib.extensions

import com.orka.myfinances.data.models.Company
import com.orka.myfinances.data.zipped.CompanyModel

fun Company.toModel(): CompanyModel {
    return CompanyModel(
        id = this.id.value,
        name = this.name,
        address = this.address,
        phone = this.phone
    )
}