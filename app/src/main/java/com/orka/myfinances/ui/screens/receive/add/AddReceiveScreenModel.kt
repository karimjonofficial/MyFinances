package com.orka.myfinances.ui.screens.receive.add

import com.orka.myfinances.data.models.folder.Category
import com.orka.myfinances.data.models.product.ProductTitle

data class AddReceiveScreenModel(
    val category: Category,
    val productTitles: List<ProductTitle>
)