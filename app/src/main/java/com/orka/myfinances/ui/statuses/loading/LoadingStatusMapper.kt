package com.orka.myfinances.ui.statuses.loading

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.orka.myfinances.R
import com.orka.myfinances.lib.ui.state.LoadingStatus

@Composable
fun LoadingStatus.str(): String {
    return when (this) {
        LoadingStatus.Unspecified -> stringResource(R.string.loading)
        LoadingStatus.Initial -> stringResource(R.string.loading)
        Refresh -> stringResource(R.string.refreshing)
        LoadMore -> stringResource(R.string.loading)
        Search -> stringResource(R.string.searching)
        ResetSearch -> stringResource(R.string.resetting_search)
        else -> stringResource(R.string.loading)
    }
}
