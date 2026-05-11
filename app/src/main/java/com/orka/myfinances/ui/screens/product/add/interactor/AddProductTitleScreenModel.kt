package com.orka.myfinances.ui.screens.product.add.interactor

import com.orka.myfinances.data.models.Id
import com.orka.myfinances.data.repositories.product.title.models.PropertyModel

data class AddProductTitleScreenModel(
    val categories: List<CategoryItemModel>,
    val categoryId: Id,
    val initialName: String = "",
    val initialPrice: Int? = null,
    val initialSalePrice: Int? = null,
    val initialExposedPrice: Int? = null,
    val initialDescription: String? = null,
    val initialProperties: List<PropertyModel<*>> = emptyList(),
    val isEditMode: Boolean = false
)
