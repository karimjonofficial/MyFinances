package com.orka.myfinances.data.repositories.product.title

import com.orka.myfinances.data.dtos.product.title.ProductTitleDto
import com.orka.myfinances.data.models.Id
import com.orka.myfinances.lib.viewmodel.Chunk

interface GetProductTitlesByCategory {
    suspend fun getByCategory(
        size: Int,
        pageIndex: Int,
        categoryId: Id,
        search: String? = null
    ): Chunk<ProductTitleDto>?
}