package com.orka.myfinances.ui.screens.product.add

import com.orka.myfinances.data.models.folder.Category
import com.orka.myfinances.ui.screens.product.add.interactor.CategoryItemModel

fun Category.toItemModel(): CategoryItemModel {
    return CategoryItemModel(
        id = id,
        title = name,
        template = template
    )
}