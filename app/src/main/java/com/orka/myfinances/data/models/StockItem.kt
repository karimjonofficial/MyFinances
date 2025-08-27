package com.orka.myfinances.data.models

data class StockItem(
    val id: Id,
    val product: Product,
    val amount: Int,
    val companyOffice: CompanyOffice
)