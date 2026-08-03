package com.orka.myfinances.ui.models.screen

import com.orka.myfinances.ui.models.item.PropertyModel
import kotlin.time.Instant

data class ProductTitleScreenModel(
    val title: String,
    val properties: List<PropertyModel>?,
    val dateTime: Instant,
    val price: Int,
    val description: String?,
    val salePrice: Int
)