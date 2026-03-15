package com.orka.myfinances.ui.screens.checkout.viewmodel

import com.orka.myfinances.data.models.Id

interface CheckoutScreenInteractor {
    fun initialize()
    fun order(id: Id, price: Int?, description: String?)
    fun sell(id: Id, price: Int?, description: String?, print: Boolean)

    companion object {
        val dummy = object : CheckoutScreenInteractor {
            override fun initialize() {}
            override fun order(id: Id, price: Int?, description: String?) {}
            override fun sell(id: Id, price: Int?, description: String?, print: Boolean) {}
        }
    }
}