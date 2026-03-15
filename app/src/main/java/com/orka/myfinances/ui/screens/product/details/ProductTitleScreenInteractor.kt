package com.orka.myfinances.ui.screens.product.details

import com.orka.myfinances.lib.ui.viewmodel.StateFul

interface ProductTitleScreenInteractor : StateFul {
    fun receive(amount: Int, totalPrice: Int, comment: String?)

    companion object {
        val dummy = object : ProductTitleScreenInteractor {
            override fun initialize() {}
            override fun receive(amount: Int, totalPrice: Int, comment: String?) {}
        }
    }
}