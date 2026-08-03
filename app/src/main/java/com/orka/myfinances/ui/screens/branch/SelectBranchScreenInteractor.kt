package com.orka.myfinances.ui.screens.branch

import com.orka.myfinances.data.models.Id
import com.orka.myfinances.lib.ui.viewmodel.Refreshable
import com.orka.myfinances.lib.ui.viewmodel.Searchable

interface SelectBranchScreenInteractor : Refreshable, Searchable {
    fun select(id: Id)
}
