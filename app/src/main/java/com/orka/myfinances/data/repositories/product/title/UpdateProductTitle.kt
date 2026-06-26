package com.orka.myfinances.data.repositories.product.title

import com.orka.myfinances.data.models.Id
import com.orka.myfinances.data.repositories.product.title.models.UpdateProductTitleRequest

interface UpdateProductTitle {
    suspend fun update(id: Id, request: UpdateProductTitleRequest): Boolean
}