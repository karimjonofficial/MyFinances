package com.orka.myfinances.testFixtures.resources.dtos

import com.orka.myfinances.data.dtos.branch.BranchDto

val branchDto1 = BranchDto(
    id = 1,
    companyId = 1,
    name = "Branch 1",
    address = "Main St 1",
    phone = "123456789"
)

val branchDtos = listOf(branchDto1)
