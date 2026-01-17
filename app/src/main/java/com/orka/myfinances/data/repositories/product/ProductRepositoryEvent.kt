package com.orka.myfinances.data.repositories.product

import com.orka.myfinances.data.models.Id

sealed interface ProductRepositoryEvent {
    data class Add(val categoryId: Id) : ProductRepositoryEvent
}
