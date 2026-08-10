package com.orka.myfinances.lib.ui.state

interface FailureStatus {
    data object Unspecified : FailureStatus
    data class Exception(val message: String) : FailureStatus {
        constructor(exception: kotlin.Exception) : this(exception.message.toString())
    }
}