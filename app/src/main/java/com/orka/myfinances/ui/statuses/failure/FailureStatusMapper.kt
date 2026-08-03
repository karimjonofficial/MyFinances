package com.orka.myfinances.ui.statuses.failure

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.orka.myfinances.R
import com.orka.myfinances.lib.ui.state.FailureStatus

@Composable
fun FailureStatus.str(): String {
    return when (this) {
        FailureStatus.Unspecified -> stringResource(R.string.failure)
        is FailureStatus.Exception -> message
        CouldNotAdd -> stringResource(R.string.could_not_add)
        is IncorrectInput -> stringResource(R.string.incorrect_input, fields.joinToString())
        DefaultCategoryNotFound -> stringResource(R.string.default_category_not_found)
        FolderNotAdded -> stringResource(R.string.folder_not_added)
        ProductTitleNotAdded -> stringResource(R.string.product_title_not_added)
        ReceiveNotAdded -> stringResource(R.string.receive_not_added)
        UserNotFound -> stringResource(R.string.user_not_found)
        CouldNotRead -> stringResource(R.string.could_not_read)
        CategoriesEmpty -> stringResource(R.string.categories_empty)
        CouldNotInsert -> stringResource(R.string.could_not_insert)
        NotInserted -> stringResource(R.string.not_inserted)
        ProductTitleNotFound -> stringResource(R.string.product_title_not_found)
        ExecutedFromLoading -> stringResource(R.string.executed_from_loading)
        CoroutineCancellationFailure -> stringResource(R.string.coroutine_cancellation)
        DatabaseFailure.Constraint -> stringResource(R.string.database_constraint)
        DatabaseFailure.CantOpen -> stringResource(R.string.database_cant_open)
        DatabaseFailure.Locked -> stringResource(R.string.database_locked)
        DatabaseFailure.Full -> stringResource(R.string.database_full)
        DatabaseFailure.ReadOnly -> stringResource(R.string.database_read_only)
        DatabaseFailure.DiskIO -> stringResource(R.string.database_disk_io)
        DatabaseFailure.Unknown -> stringResource(R.string.database_unknown)
        NetworkFailure.NoInternet -> stringResource(R.string.no_internet)
        NetworkFailure.ConnectTimeout -> stringResource(R.string.connect_timeout)
        NetworkFailure.SocketTimeout -> stringResource(R.string.socket_timeout)
        NetworkFailure.RequestTimeout -> stringResource(R.string.request_timeout)
        is NetworkFailure.Http -> stringResource(R.string.http_error, statusCode)
        ExecutedFromFailure -> stringResource(R.string.executed_from_failure)
        LoadedAbsentPage -> stringResource(R.string.loaded_absent_page)
        EmptyDataFailure -> stringResource(R.string.empty_data)
        CalledSearchMoreWithNullQuery -> stringResource(R.string.called_search_more_with_null_query)
        else -> stringResource(R.string.unresolved_error)
    }
}
