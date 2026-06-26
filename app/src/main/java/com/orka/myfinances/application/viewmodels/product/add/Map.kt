package com.orka.myfinances.application.viewmodels.product.add

import com.orka.myfinances.data.dtos.folder.CategoryDto
import com.orka.myfinances.data.models.Id
import com.orka.myfinances.data.repositories.folder.toEntity
import com.orka.myfinances.ui.screens.product.add.interactor.CategoryItemModel

fun CategoryDto.toItemModel(): CategoryItemModel {
    return CategoryItemModel(
        id = Id(id),
        title = name,
        template = template.toEntity()
    )
}