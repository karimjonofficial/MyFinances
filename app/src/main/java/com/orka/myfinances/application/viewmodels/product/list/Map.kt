package com.orka.myfinances.application.viewmodels.product.list

import com.orka.myfinances.R
import com.orka.myfinances.data.dtos.product.title.ProductTitleDto
import com.orka.myfinances.data.models.Id
import com.orka.myfinances.lib.ui.models.UiText
import com.orka.myfinances.ui.screens.product.list.ProductTitleCardModel
import com.orka.myfinances.ui.screens.product.list.ProductTitleUiModel


fun ProductTitleDto.toCardModel(): ProductTitleCardModel {
    return ProductTitleCardModel(
        title = name,
        description = if(description != null) UiText.Str(description) else UiText.Res(R.string.no_description_provided)
    )
}

fun ProductTitleDto.toUiModel(): ProductTitleUiModel {
    return ProductTitleUiModel(
        model = toCardModel(),
        id = Id(id)
    )
}