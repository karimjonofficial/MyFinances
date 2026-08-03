package com.orka.myfinances.ui.statuses.failure

import com.orka.myfinances.lib.ui.state.FailureStatus

sealed interface DatabaseFailure : FailureStatus {
    data object Constraint : DatabaseFailure
    data object CantOpen : DatabaseFailure
    data object Locked : DatabaseFailure
    data object Full : DatabaseFailure
    data object ReadOnly : DatabaseFailure
    data object DiskIO : DatabaseFailure
    data object Unknown : DatabaseFailure
}
