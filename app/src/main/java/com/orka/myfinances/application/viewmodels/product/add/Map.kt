package com.orka.myfinances.application.viewmodels.product.add

import com.orka.myfinances.data.api.folder.toEntity
import com.orka.myfinances.data.api.title.models.response.PropertyApiModel
import com.orka.myfinances.data.models.Id
import com.orka.myfinances.data.models.product.Property
import com.orka.myfinances.fixtures.resources.Types

fun PropertyApiModel.toEntity(): Property {
    return Property(
        id = Id(id),
        field = field.toEntity(),
        value = when(field.type) {
            Types.TEXT -> value
            Types.NUMBER -> value.toInt()
            Types.BOOLEAN -> value.toBoolean()
            else -> throw Exception()
        }
    )
}