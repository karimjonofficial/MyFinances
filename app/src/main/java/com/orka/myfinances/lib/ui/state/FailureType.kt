package com.orka.myfinances.lib.ui.state

interface FailureType {
    data object Unspecified : FailureType
    data class Exception(val message: String) : FailureType
}