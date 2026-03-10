package com.orka.myfinances.ui.screens.checkout.viewmodel

import com.orka.myfinances.data.models.Client

interface CheckoutScreenInteractor {
    fun initialize()
    fun order(client: Client?, price: Int?, description: String?)
    fun sell(client: Client?, price: Int?, description: String?, print: Boolean)

    companion object {
        val dummy = object : CheckoutScreenInteractor {
            override fun initialize() {}

            override fun order(
                client: Client?,
                price: Int?,
                description: String?
            ) {}

            override fun sell(
                client: Client?,
                price: Int?,
                description: String?,
                print: Boolean
            ) {}
        }
    }
}