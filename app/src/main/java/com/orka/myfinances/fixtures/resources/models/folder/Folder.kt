package com.orka.myfinances.fixtures.resources.models.folder

import com.orka.myfinances.data.models.folder.Catalog
import com.orka.myfinances.data.models.folder.Warehouse
import com.orka.myfinances.fixtures.resources.models.id1
import com.orka.myfinances.fixtures.resources.models.id2
import com.orka.myfinances.fixtures.resources.models.id3
import com.orka.myfinances.fixtures.resources.models.id4
import com.orka.myfinances.fixtures.resources.models.template.template1
import com.orka.myfinances.fixtures.resources.models.template.template2
import com.orka.myfinances.fixtures.resources.models.template.template3

val folder1 = Warehouse(
    id = id1,
    name = "Smartphones",
    template = template1
)
val folder2 = Warehouse(
    id = id2,
    name = "Laptops",
    template = template2
)
val folder3 = Warehouse(
    id = id4,
    name = "Standalone Gadgets",
    template = template3
)
val folder4 = Catalog(
    id = id3,
    name = "Electronics"
)

val folders = listOf(folder1, folder2, folder4)
val warehouses = listOf(folder1, folder2, folder3)