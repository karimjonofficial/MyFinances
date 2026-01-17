package com.orka.myfinances.data.repositories.product

import com.orka.myfinances.data.models.Id
import com.orka.myfinances.data.models.product.ProductTitle
import com.orka.myfinances.fixtures.resources.models.product.productTitles
import com.orka.myfinances.lib.fixtures.data.repositories.MockGetByIdRepository

class ProductTitleRepository(items: List<ProductTitle> = productTitles) : MockGetByIdRepository<ProductTitle>(items) {
    override fun find(id: Id, item: ProductTitle): Boolean {
        return item.id == id
    }
}