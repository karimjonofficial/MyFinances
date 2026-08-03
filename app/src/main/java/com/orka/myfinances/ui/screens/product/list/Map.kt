package com.orka.myfinances.ui.screens.product.list

import com.orka.myfinances.data.models.product.ProductTitle
import com.orka.myfinances.ui.models.card.ProductTitleCardModel

fun ProductTitle.toCardModel(): ProductTitleCardModel {
    return ProductTitleCardModel(
        title = name,
        description = description
    )
}