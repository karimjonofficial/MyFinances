package com.orka.myfinances.ui.statuses.failure

import com.orka.myfinances.lib.ui.state.FailureStatus

sealed interface NetworkFailure : FailureStatus {
    data object NoInternet : NetworkFailure
    data object ConnectTimeout : NetworkFailure
    data object SocketTimeout : NetworkFailure
    data object RequestTimeout : NetworkFailure
    data class Http(val statusCode: Int) : FailureStatus
}
