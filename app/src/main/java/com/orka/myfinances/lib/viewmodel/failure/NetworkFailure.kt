package com.orka.myfinances.lib.viewmodel.failure

import com.orka.myfinances.lib.ui.state.FailureType

sealed interface NetworkFailure : FailureType {
    data object NoInternet : NetworkFailure
    data object ConnectTimeout : NetworkFailure
    data object SocketTimeout : NetworkFailure
    data object RequestTimeout : NetworkFailure
    data class Http(val statusCode: Int) : FailureType
}