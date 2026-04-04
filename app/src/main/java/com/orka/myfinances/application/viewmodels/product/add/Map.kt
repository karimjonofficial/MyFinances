package com.orka.myfinances.application.viewmodels.product.add

import com.orka.myfinances.data.api.folder.models.response.CategoryApiModel
import com.orka.myfinances.data.api.folder.toEntity
import com.orka.myfinances.data.models.Id
import com.orka.myfinances.ui.screens.product.add.interactor.CategoryItemModel

fun CategoryApiModel.toItemModel(): CategoryItemModel {
    return CategoryItemModel(
        id = Id(id),
        title = name,
        template = template.toEntity()
    )
}