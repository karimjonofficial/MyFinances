package com.orka.myfinances.testFixtures.resources.dtos

import com.orka.myfinances.data.dtos.folder.CatalogDto
import com.orka.myfinances.data.dtos.folder.CategoryDto

val categoryDto1 = CategoryDto(
    id = 1,
    name = "Category 1",
    template = templateDto1
)

val catalogDto1 = CatalogDto(
    id = 2,
    name = "Catalog 1"
)

val folderDtos = listOf(categoryDto1, catalogDto1)
