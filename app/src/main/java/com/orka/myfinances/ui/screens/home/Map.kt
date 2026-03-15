package com.orka.myfinances.ui.screens.home

import com.orka.myfinances.R
import com.orka.myfinances.data.models.product.ProductTitle
import com.orka.myfinances.lib.ui.models.UiText
import com.orka.myfinances.ui.screens.home.components.ProductTitleCardModel

fun ProductTitle.toCardModel(): ProductTitleCardModel {
    return ProductTitleCardModel(
        title = name,
        description = if(description.isNullOrBlank()) UiText.Res(R.string.no_description_provided) else UiText.Str(description)
    )
}