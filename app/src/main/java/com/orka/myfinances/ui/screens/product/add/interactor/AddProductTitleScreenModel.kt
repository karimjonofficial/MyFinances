package com.orka.myfinances.ui.screens.product.add.interactor

import com.orka.myfinances.data.models.Id

data class AddProductTitleScreenModel(
    val categories: List<CategoryItemModel>,
    val categoryId: Id
)