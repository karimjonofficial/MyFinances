package com.orka.myfinances.application.viewmodels.folder.category

import com.orka.myfinances.data.api.folder.models.response.CategoryApiModel
import com.orka.myfinances.data.models.Id
import com.orka.myfinances.ui.screens.folder.category.CategoryScreenModel

fun CategoryApiModel.toScreenModel(): CategoryScreenModel {
    return CategoryScreenModel(
        id = Id(id),
        title = name
    )
}