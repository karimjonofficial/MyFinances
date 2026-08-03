package com.orka.myfinances.ui.screens.receive.add

import com.orka.myfinances.lib.ui.viewmodel.Refreshable
import com.orka.myfinances.ui.models.item.ProductTitleItemModel

interface AddReceiveScreenInteractor : Refreshable {
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