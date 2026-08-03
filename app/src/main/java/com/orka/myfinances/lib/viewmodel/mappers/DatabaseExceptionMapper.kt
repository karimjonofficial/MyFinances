package com.orka.myfinances.lib.viewmodel.mappers

import android.database.sqlite.SQLiteCantOpenDatabaseException
import android.database.sqlite.SQLiteConstraintException
import android.database.sqlite.SQLiteDatabaseLockedException
import android.database.sqlite.SQLiteDiskIOException
import android.database.sqlite.SQLiteException
import androidx.sqlite.SQLiteException as AndroidXSQLiteException
import com.orka.myfinances.lib.ui.state.FailureStatus
import com.orka.myfinances.lib.ui.state.State
import com.orka.myfinances.lib.viewmodel.base.ExceptionMapper
import com.orka.myfinances.ui.statuses.failure.CoroutineCancellationFailure
import com.orka.myfinances.ui.statuses.failure.DatabaseFailure
import kotlin.coroutines.cancellation.CancellationException

class DatabaseExceptionMapper<T> : ExceptionMapper<T> {
    override suspend fun map(
        oldState: State<T>?,
        e: Exception
    ): State<T> {
        val failure = when (e) {
            is CancellationException -> CoroutineCancellationFailure
            is SQLiteConstraintException -> DatabaseFailure.Constraint
            is SQLiteCantOpenDatabaseException -> DatabaseFailure.CantOpen
            is SQLiteDatabaseLockedException -> DatabaseFailure.Locked
            is android.database.sqlite.SQLiteFullException -> DatabaseFailure.Full
            is android.database.sqlite.SQLiteReadOnlyDatabaseException -> DatabaseFailure.ReadOnly
            is SQLiteDiskIOException -> DatabaseFailure.DiskIO
            is SQLiteException -> DatabaseFailure.Unknown
            is AndroidXSQLiteException -> DatabaseFailure.Unknown
            else -> FailureStatus.Exception(e.message.orEmpty())
        }

        return State.Failure(
            status = failure,
            value = oldState?.value
        )
    }
}