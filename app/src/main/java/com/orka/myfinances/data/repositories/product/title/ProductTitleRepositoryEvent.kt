package com.orka.myfinances.data.repositories.product.title

import com.orka.myfinances.data.models.Id

sealed interface ProductTitleRepositoryEvent {
    data class Add(val categoryId: Id) : ProductTitleRepositoryEvent
}
