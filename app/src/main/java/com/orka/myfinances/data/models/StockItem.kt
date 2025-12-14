package com.orka.myfinances.data.models

import com.orka.myfinances.data.models.product.Product

data class StockItem(
    val id: Id,
    val product: Product,
    val amount: Int,
    val companyOffice: CompanyOffice,
    val description: String? = null
)