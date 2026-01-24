package com.orka.myfinances.data.repositories.product

import com.orka.myfinances.data.models.Id
import com.orka.myfinances.data.models.product.ProductTitle
import com.orka.myfinances.fixtures.resources.models.product.productTitles
import com.orka.myfinances.lib.fixtures.data.repositories.MockGetByIdRepository

class ProductTitleRepository : MockGetByIdRepository<ProductTitle> {
    override val items = productTitles.toMutableList()

    override suspend fun List<ProductTitle>.find(id: Id): ProductTitle? {
        return this.find { it.id == id }
    }
}