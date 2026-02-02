package com.orka.myfinances.fixtures.resources.models.folder

import com.orka.myfinances.data.models.Id
import com.orka.myfinances.data.models.folder.Catalog
import com.orka.myfinances.data.models.folder.Category
import com.orka.myfinances.fixtures.resources.models.id1
import com.orka.myfinances.fixtures.resources.models.id2
import com.orka.myfinances.fixtures.resources.models.id3
import com.orka.myfinances.fixtures.resources.models.template.template1
import com.orka.myfinances.fixtures.resources.models.template.template2
import com.orka.myfinances.fixtures.resources.models.template.template4

val category1 = Category(
    id = id1,
    name = "Smartphones",
    template = template1
)
val category2 = Category(
    id = id2,
    name = "Laptops",
    template = template2
)
val catalog1 = Catalog(
    id = id3,
    name = "Electronics"
)
val category3 = Category(
    id = Id(3),
    name = "Yangi",
    template = template4
)

val folders = listOf(category1, category2, category3, catalog1)
val categories = listOf(category1, category2, category3)