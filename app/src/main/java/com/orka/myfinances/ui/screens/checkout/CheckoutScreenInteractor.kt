package com.orka.myfinances.ui.screens.checkout

import com.orka.myfinances.data.models.Id
import com.orka.myfinances.lib.ui.viewmodel.Refreshable
import kotlinx.datetime.LocalDate

interface CheckoutScreenInteractor : Refreshable {
    fun order(clientId: Id, price: Int?, description: String?, endDate: LocalDate)
    fun sell(clientId: Id, price: Int?, description: String?, print: Boolean)
    fun debt(clientId: Id, price: Int?, description: String?, print: Boolean, dueDate: LocalDate)

    companion object {
        val dummy = object : CheckoutScreenInteractor {
            override fun order(clientId: Id, price: Int?, description: String?, endDate: LocalDate) {}
            override fun sell(clientId: Id, price: Int?, description: String?, print: Boolean) {}
            override fun debt(clientId: Id, price: Int?, description: String?, print: Boolean, dueDate: LocalDate) {}
            override fun refresh() {}
        }
    }
}
