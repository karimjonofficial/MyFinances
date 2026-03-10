package com.orka.myfinances.ui.screens.receive.add

import com.orka.myfinances.data.models.product.ProductTitle
import com.orka.myfinances.lib.ui.viewmodel.StateFul

interface AddReceiveScreenInteractor : StateFul {
    fun add(title: ProductTitle?, amount: Int?, price: Int?, salePrice: Int?, totalPrice: Int?, description: String?)

    companion object {
        val dummy = object : AddReceiveScreenInteractor {
            override fun add(title: ProductTitle?, amount: Int?, price: Int?, salePrice: Int?, totalPrice: Int?, description: String?) {}
            override fun initialize() {}
        }
    }
}