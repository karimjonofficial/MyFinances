package com.orka.myfinances.ui.models.item

import com.orka.myfinances.data.models.Id
import com.orka.myfinances.lib.ui.models.SelectionItemModel

data class CategoryItemModel(
    val id: Id,
    override val title: String,
    override val description: String? = null,
    override val leadingIconRes: Int? = null,
) : SelectionItemModel