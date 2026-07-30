package com.orka.myfinances.ui.screens.product.list

import com.orka.myfinances.data.models.product.ProductTitle

fun ProductTitle.toCardModel(): ProductTitleCardModel {
    return ProductTitleCardModel(
        title = name,
        description = description
    )
}