package com.orka.myfinances.data.models.sale

import com.orka.myfinances.data.models.Id
import com.orka.myfinances.data.models.product.Product

data class SaleItem(
    val id: Id,
    val product: Product,
    val amount: Int
)