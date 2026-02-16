package com.orka.myfinances.ui.screens.order

data class OrderCardModel(
    val title: String,
    val price: String,
    val dateTime: String,
    val size: String,
    val completed: Boolean
)