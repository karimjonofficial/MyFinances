package com.orka.myfinances.ui.screens.branch

import com.orka.myfinances.data.models.Id
import com.orka.myfinances.lib.ui.viewmodel.StateFul

interface SelectBranchScreenInteractor : StateFul {
    fun select(id: Id)
    fun search(query: String)
}