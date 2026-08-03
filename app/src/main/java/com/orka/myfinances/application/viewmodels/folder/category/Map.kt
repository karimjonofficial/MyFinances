package com.orka.myfinances.application.viewmodels.folder.category

import com.orka.myfinances.data.dtos.folder.CategoryDto
import com.orka.myfinances.data.models.Id
import com.orka.myfinances.ui.models.screen.CategoryScreenModel

fun CategoryDto.toScreenModel(): CategoryScreenModel {
    return CategoryScreenModel(
        id = Id(id),
        title = name,
        exposed = false
    )
}