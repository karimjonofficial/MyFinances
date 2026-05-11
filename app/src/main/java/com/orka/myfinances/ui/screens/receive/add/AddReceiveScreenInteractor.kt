package com.orka.myfinances.ui.screens.receive.add

import com.orka.myfinances.lib.ui.viewmodel.StateFul

interface AddReceiveScreenInteractor : StateFul {
    fun add(
        title: ProductTitleItemModel?,
        amount: Int?,
        price: Int?,
        salePrice: Int?,
        exposedPrice: Int?,
        totalPrice: Int?,
        description: String?
    )

    companion object {
        val dummy = object : AddReceiveScreenInteractor {
            override fun initialize() {}
            override fun refresh() {}
            override fun add(
                title: ProductTitleItemModel?,
                amount: Int?,
                price: Int?,
                salePrice: Int?,
                exposedPrice: Int?,
                totalPrice: Int?,
                description: String?
            ) {}
        }
    }
}