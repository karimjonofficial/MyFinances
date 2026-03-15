package com.orka.myfinances.ui.screens.product.details.models

data class ProductTitleScreenModel(
    val title: String,
    val properties: List<PropertyModel>,
    val dateTime: String,
    val price: String,
    val description: String?,
    val salePrice: Int
)