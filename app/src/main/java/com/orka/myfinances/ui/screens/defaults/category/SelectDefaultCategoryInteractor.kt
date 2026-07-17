package com.orka.myfinances.ui.screens.defaults.category

import com.orka.myfinances.data.models.Id

interface SelectDefaultCategoryInteractor {
    fun refresh()
    fun select(id: Id)
}
