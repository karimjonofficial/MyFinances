package com.orka.myfinances.lib.viewmodel.failure

import com.orka.myfinances.lib.ui.state.FailureType

sealed interface DatabaseFailure : FailureType {

    /** Constraint violated (PRIMARY KEY, UNIQUE, FOREIGN KEY, etc.) */
    data object Constraint : DatabaseFailure

    /** Database file couldn't be opened. */
    data object CantOpen : DatabaseFailure

    /** Database is locked by another transaction/process. */
    data object Locked : DatabaseFailure

    /** Storage is full. */
    data object Full : DatabaseFailure

    /** Attempted to write to a read-only database. */
    data object ReadOnly : DatabaseFailure

    /** General disk I/O failure. */
    data object DiskIO : DatabaseFailure

    /** Any other SQLite-related error. */
    data object Unknown : DatabaseFailure
}