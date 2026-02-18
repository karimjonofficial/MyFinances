package com.orka.myfinances.ui.screens.product

data class ProductTitleModel(
    val title: String,
    val properties: List<PropertyModel>,
    val dateTime: String,
    val price: String,
    val description: String?
)