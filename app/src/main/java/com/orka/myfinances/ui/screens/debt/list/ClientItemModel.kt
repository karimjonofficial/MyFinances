package com.orka.myfinances.ui.screens.debt.list

import com.orka.myfinances.data.models.Id
import com.orka.myfinances.lib.ui.models.ItemModel

data class ClientItemModel(
    val id: Id,
    override val title: String
) : ItemModel
