package com.orka.myfinances.data.models.template

sealed interface FieldType {
    data object Text : FieldType
    data object Integer : FieldType
}