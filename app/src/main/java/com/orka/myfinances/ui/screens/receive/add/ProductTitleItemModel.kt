package com.orka.myfinances.ui.screens.receive.add

import com.orka.myfinances.data.models.Id
import com.orka.myfinances.lib.ui.models.BottomSheetItemModel

data class ProductTitleItemModel(
    override val id: Id,
    override val title: String,
    val defaultPrice: Int,
    val defaultSalePrice: Int,
    val defaultExposedPrice: Int
) : BottomSheetItemModel
