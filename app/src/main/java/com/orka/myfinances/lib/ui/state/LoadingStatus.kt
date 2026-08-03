package com.orka.myfinances.lib.ui.state

interface LoadingStatus {
    data object Unspecified : LoadingStatus
    data object Initial : LoadingStatus
}