package com.orka.myfinances.ui.screens.product.edit

import com.orka.myfinances.data.models.Id
import com.orka.myfinances.data.repositories.product.title.models.PropertyModel
import com.orka.myfinances.lib.ui.viewmodel.StateFul

interface EditProductTitleScreenInteractor : StateFul {
    fun updateProductTitle(
        properties: List<PropertyModel<*>?>,
        name: String,
        price: Int?,
        salePrice: Int?,
        exposedPrice: Int?,
        description: String?,
        categoryId: Id
    )

    companion object {
        val dummy = object : EditProductTitleScreenInteractor {
            override fun updateProductTitle(
                properties: List<PropertyModel<*>?>,
                name: String,
                price: Int?,
                salePrice: Int?,
                exposedPrice: Int?,
                description: String?,
                categoryId: Id
            ) {}

            override fun initialize() {}
            override fun refresh() {}
        }
    }
}
