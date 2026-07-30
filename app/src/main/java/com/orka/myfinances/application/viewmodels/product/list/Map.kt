package com.orka.myfinances.application.viewmodels.product.list

import com.orka.myfinances.data.dtos.product.title.ProductTitleDto
import com.orka.myfinances.data.models.Id
import com.orka.myfinances.ui.screens.product.list.ProductTitleCardModel
import com.orka.myfinances.ui.screens.product.list.ProductTitleUiModel


fun ProductTitleDto.toCardModel(): ProductTitleCardModel {
    return ProductTitleCardModel(
        title = name,
        description = description
    )
}

fun ProductTitleDto.toUiModel(): ProductTitleUiModel {
    return ProductTitleUiModel(
        model = toCardModel(),
        id = Id(id)
    )
}