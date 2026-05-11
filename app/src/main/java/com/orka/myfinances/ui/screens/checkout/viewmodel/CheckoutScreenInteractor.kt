package com.orka.myfinances.ui.screens.checkout.viewmodel

import com.orka.myfinances.data.models.Id
import com.orka.myfinances.lib.ui.viewmodel.StateFul

interface CheckoutScreenInteractor : StateFul {
    fun order(clientId: Id, price: Int?, description: String?)
    fun order(
        firstName: String,
        lastName: String?,
        patronymic: String?,
        phone: String?,
        address: String?,
        price: Int?,
        description: String?
    )

    fun sell(clientId: Id, price: Int?, description: String?, print: Boolean)
    fun sell(
        firstName: String,
        lastName: String?,
        patronymic: String?,
        phone: String?,
        address: String?,
        price: Int?,
        description: String?,
        print: Boolean
    )

    companion object {
        val dummy = object : CheckoutScreenInteractor {
            override fun order(clientId: Id, price: Int?, description: String?) {}
            override fun order(
                firstName: String,
                lastName: String?,
                patronymic: String?,
                phone: String?,
                address: String?,
                price: Int?,
                description: String?
            ) {
            }

            override fun sell(clientId: Id, price: Int?, description: String?, print: Boolean) {}
            override fun sell(
                firstName: String,
                lastName: String?,
                patronymic: String?,
                phone: String?,
                address: String?,
                price: Int?,
                description: String?,
                print: Boolean
            ) {
            }

            override fun initialize() {}
            override fun refresh() {}
        }
    }
}
