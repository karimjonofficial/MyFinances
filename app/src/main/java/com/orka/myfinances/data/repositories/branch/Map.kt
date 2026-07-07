package com.orka.myfinances.data.repositories.branch

import com.orka.myfinances.data.api.branch.BranchApiModel
import com.orka.myfinances.data.dtos.branch.BranchDto

fun BranchApiModel.toDto(): BranchDto {
    return BranchDto(
        id = id,
        companyId = companyId,
        name = name,
        address = address,
        phone = phone
    )
}
