package com.orka.myfinances.application.viewmodels.select

import com.orka.myfinances.data.dtos.folder.CategoryDto
import com.orka.myfinances.data.models.Id
import com.orka.myfinances.ui.models.item.CategoryItemModel

fun CategoryDto.toItemModel(): CategoryItemModel {
    return CategoryItemModel(
        id = Id(id),
        title = name
    )
}