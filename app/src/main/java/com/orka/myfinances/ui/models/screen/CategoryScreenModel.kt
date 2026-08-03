package com.orka.myfinances.ui.models.screen

import com.orka.myfinances.data.models.Id

data class CategoryScreenModel(
    val id: Id,
    val title: String,
    val exposed: Boolean
)