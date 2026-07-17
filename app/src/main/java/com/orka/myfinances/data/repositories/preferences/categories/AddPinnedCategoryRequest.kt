package com.orka.myfinances.data.repositories.preferences.categories

import com.orka.myfinances.data.models.Id

data class AddPinnedCategoryRequest(
    val id: Id,
    val index: Int? = null
)
