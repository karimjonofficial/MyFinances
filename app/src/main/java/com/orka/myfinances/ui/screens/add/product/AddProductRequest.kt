package com.orka.myfinances.ui.screens.add.product

data class AddProductRequest(
    val name: String,
    val warehouseId: Int,
    val price: Int,
    val salePrice: Int,
    val properties: List<PropertyModel<*>>,
    val description: String
)