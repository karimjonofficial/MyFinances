package com.orka.myfinances.models

sealed interface FieldType {
    data object Text : FieldType
    data object Number : FieldType
    data object Boolean : FieldType
}