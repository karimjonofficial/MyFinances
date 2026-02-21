package com.orka.myfinances.fixtures.format

import com.orka.myfinances.data.models.product.ProductTitle
import com.orka.myfinances.lib.format.FormatNames

class FormatNamesImpl : FormatNames {
    override fun formatNames(items: List<ProductTitle>): String {
        return items.joinToString { it.name }
    }
}