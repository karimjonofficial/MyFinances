package com.orka.myfinances.ui.models.item

import com.orka.myfinances.data.models.Id
import com.orka.myfinances.lib.ui.models.BottomSheetItemModel
import com.orka.myfinances.lib.ui.models.SelectionItemModel

data class ClientItemModel(
    override val id: Id,
    override val title: String,
    override val description: String? = null,
    override val leadingIconRes: Int? = null,
) : BottomSheetItemModel, SelectionItemModel
