package com.orka.myfinances.lib.ui.state

interface LoadingType {
    data object Unspecified : LoadingType
    data object Initial : LoadingType
}