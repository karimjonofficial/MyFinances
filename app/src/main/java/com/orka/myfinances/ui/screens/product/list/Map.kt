package com.orka.myfinances.ui.screens.product.list

import com.orka.myfinances.R
import com.orka.myfinances.data.models.product.ProductTitle
import com.orka.myfinances.lib.ui.models.UiText

fun ProductTitle.toCardModel(): ProductTitleCardModel {
    return ProductTitleCardModel(
        title = name,
        description = if(description.isNullOrBlank()) UiText.Res(R.string.no_description_provided) else UiText.Str(description)
    )
}