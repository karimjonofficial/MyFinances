package com.orka.myfinances.application.viewmodels.receive.bottomsheet

import com.orka.myfinances.data.api.title.models.response.ProductTitleApiModel
import com.orka.myfinances.data.models.Id
import com.orka.myfinances.ui.screens.receive.add.ProductTitleItemModel

fun ProductTitleApiModel.toItemModel(): ProductTitleItemModel {
    return ProductTitleItemModel(
        id = Id(id),
        title = name,
        defaultPrice = defaultPrice.toInt(),
        defaultSalePrice = defaultSalePrice.toInt()
    )
}
