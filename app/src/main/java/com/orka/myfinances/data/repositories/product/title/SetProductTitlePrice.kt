package com.orka.myfinances.data.repositories.product.title

import com.orka.myfinances.data.models.Id
import com.orka.myfinances.data.models.product.ProductTitle

fun interface SetProductTitlePrice {
    suspend fun setPrice(id: Id, price: Int, salePrice: Int): ProductTitle
}