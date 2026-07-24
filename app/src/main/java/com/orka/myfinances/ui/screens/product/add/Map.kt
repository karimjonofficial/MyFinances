package com.orka.myfinances.ui.screens.product.add

import com.orka.myfinances.data.models.folder.Category
import com.orka.myfinances.ui.screens.product.add.interactor.CategoryBottomSheetItemModel

fun Category.toItemModel(): CategoryBottomSheetItemModel {
    return CategoryBottomSheetItemModel(
        id = id,
        title = name,
        template = template
    )
}