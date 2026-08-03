package com.orka.myfinances.ui.screens.product.details

import com.orka.myfinances.lib.ui.viewmodel.Refreshable

interface ProductTitleScreenInteractor : Refreshable {
    fun edit()
    fun receive(amount: Int, totalPrice: Int, comment: String?)

    companion object {
        val dummy = object : ProductTitleScreenInteractor {
            override fun edit() {}
            override fun receive(amount: Int, totalPrice: Int, comment: String?) {}
            override fun refresh() {}
        }
    }
}
