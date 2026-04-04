package com.orka.myfinances.ui.screens.product.add.interactor

import com.orka.myfinances.data.models.Id
import com.orka.myfinances.data.models.template.Template
import com.orka.myfinances.lib.ui.models.BottomSheetItemModel

data class CategoryItemModel(
    val id: Id,
    override val title: String,
    val template: Template
) : BottomSheetItemModel