package com.orka.myfinances.ui.screens.product.add

import com.orka.myfinances.data.models.Id
import com.orka.myfinances.data.repositories.product.title.models.PropertyModel
import com.orka.myfinances.lib.ui.viewmodel.Refreshable

interface AddProductTitleScreenInteractor : Refreshable {
    fun addProductTitle(
        properties: List<PropertyModel<*>?>,
        name: String,
        price: Int?,
        salePrice: Int?,
        exposedPrice: Int?,
        description: String?,
        categoryId: Id
    )

    companion object {
        val dummy = object : AddProductTitleScreenInteractor {
            override fun addProductTitle(
                properties: List<PropertyModel<*>?>,
                name: String,
                price: Int?,
                salePrice: Int?,
                exposedPrice: Int?,
                description: String?,
                categoryId: Id
            ) {}

            override fun refresh() {}
        }
    }
}
