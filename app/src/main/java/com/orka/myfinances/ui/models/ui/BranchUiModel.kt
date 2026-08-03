package com.orka.myfinances.ui.models.ui

import com.orka.myfinances.data.models.Id
import com.orka.myfinances.lib.ui.models.SelectionItemModel

data class BranchUiModel(
    override val title: String,
    val branchId: Id,
    override val description: String? = null,
    override val leadingIconRes: Int? = null
) : SelectionItemModel