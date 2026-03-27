package com.orka.myfinances.ui.screens.debt.details.interactor

import com.orka.myfinances.data.models.Id
import com.orka.myfinances.lib.ui.viewmodel.StateFul

interface DebtScreenInteractor : StateFul {
    fun back()
    fun navigateToClient(id: Id)

    companion object {
        val dummy = object : DebtScreenInteractor {
            override fun back() {}
            override fun navigateToClient(id: Id) {}
            override fun refresh() {}
        }
    }
}