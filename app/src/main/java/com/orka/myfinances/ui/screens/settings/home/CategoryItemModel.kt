package com.orka.myfinances.ui.screens.settings.home

import com.orka.myfinances.data.models.Id

data class CategoryItemModel(
    val id: Id,
    override val title: String,
    override val description: String? = null,
    override val leadingIconRes: Int? = null,
) : SelectionItemModel