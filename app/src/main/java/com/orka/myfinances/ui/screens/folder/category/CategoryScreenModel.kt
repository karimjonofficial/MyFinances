package com.orka.myfinances.ui.screens.folder.category

import com.orka.myfinances.data.models.Id

data class CategoryScreenModel(
    val id: Id,
    val title: String,
    val exposed: Boolean
)
