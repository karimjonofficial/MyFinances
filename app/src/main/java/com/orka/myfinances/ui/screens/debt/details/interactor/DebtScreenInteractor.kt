package com.orka.myfinances.ui.screens.debt.details.interactor

import com.orka.myfinances.data.models.Id
import com.orka.myfinances.lib.ui.viewmodel.Refreshable

interface DebtScreenInteractor : Refreshable {
    fun back()
    fun navigateToClient(id: Id)
    fun setNotified(notified: Boolean)
    fun setPaid()

    companion object {
        val dummy = object : DebtScreenInteractor {
            override fun back() {}
            override fun navigateToClient(id: Id) {}
            override fun initialize() {}
            override fun refresh() {}
            override fun setNotified(notified: Boolean) {}
            override fun setPaid() {}
        }
    }
}