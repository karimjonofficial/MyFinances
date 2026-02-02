package com.orka.myfinances.data.repositories.product.title.models

import com.orka.myfinances.data.models.Id

data class PropertyModel<T: Any>(
    val fieldId: Id,
    val value: T
)