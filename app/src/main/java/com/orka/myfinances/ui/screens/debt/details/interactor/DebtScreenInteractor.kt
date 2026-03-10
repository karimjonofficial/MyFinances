package com.orka.myfinances.ui.screens.debt.details.interactor

import com.orka.myfinances.data.models.Id
import com.orka.myfinances.lib.ui.viewmodel.StateFul

interface DebtScreenInteractor : StateFul {
    fun back()
    fun navigateToClient(id: Id)
}