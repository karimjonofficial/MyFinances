package com.orka.myfinances.fixtures.resources.models

import com.orka.myfinances.data.models.Branch

val branch1 = Branch(
    id = id1,
    name = "Branch 1",
    company = company1,
    address = "Address of the Branch 1",
    phone = "Phone of the Branch 1"
)
val branch2 = Branch(
    id = id2,
    name = "Branch 2",
    company = company1,
    address = "Address of the Branch 2",
    phone = "Phone of the Branch 2"
)

val branches = listOf(branch1, branch2)