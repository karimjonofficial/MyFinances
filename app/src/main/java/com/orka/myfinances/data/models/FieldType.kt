package com.orka.myfinances.data.models

sealed interface FieldType {
    data object Text : FieldType
    data object Integer : FieldType
    data object Float : FieldType
    data object Boolean : FieldType
}