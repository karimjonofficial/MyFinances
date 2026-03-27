package com.orka.myfinances.ui.screens.checkout.viewmodel

import com.orka.myfinances.data.models.Id
import com.orka.myfinances.lib.ui.viewmodel.StateFul

interface CheckoutScreenInteractor : StateFul {
    fun order(id: Id, price: Int?, description: String?)
    fun sell(id: Id, price: Int?, description: String?, print: Boolean)

    companion object {
        val dummy = object : CheckoutScreenInteractor {
            override fun order(id: Id, price: Int?, description: String?) {}
            override fun sell(id: Id, price: Int?, description: String?, print: Boolean) {}
            override fun refresh() {}
        }
    }
}